package com.maxwell3025.vats.content;

import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.api.ChemicalReaction;
import com.maxwell3025.vats.api.Mixture;
import com.maxwell3025.vats.content.chemEngine.ChemicalTickEvent;
import it.unimi.dsi.fastutil.objects.Object2DoubleMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import java.util.List;
import java.util.Map;

public class ChemicalMixBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    int time = 0;
    private static BlockEntityType<ChemicalMixBlockEntity> typeInstance = null;
    private static List<ChemicalReaction> reactionList = null;

    public double getHeat() {
        return heat;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    private double heat;
    private static final double BLOCK_VOLUME = 1000;
    @NonNull
    private Mixture contents = new Mixture();

    public static BlockEntityType<ChemicalMixBlockEntity> getTypeInstance() {
        if (typeInstance == null) {
            typeInstance = BlockEntityType.Builder.of(ChemicalMixBlockEntity::new, ChemicalMixBlock.getInstance()).build(null);
        }
        return typeInstance;
    }

    public ChemicalMixBlockEntity(BlockPos pos, BlockState state) {
        super(getTypeInstance(), pos, state);
        state.getBlock();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Mixture getContents() {
        return contents.scale(1);
    }

    public void setContents(Mixture addedContents) {
        contents = new Mixture(addedContents);
    }

    public void addContents(Mixture addedContents) {
        contents = contents.add(addedContents);
    }

    @NonNull
    public List<ChemicalReaction> getReactionList() {
        assert level != null;
        if (reactionList == null) {
            reactionList = this.level.getRecipeManager().getAllRecipesFor(ChemicalReaction.getTypeInstance());
        }
        return reactionList;
    }

    /**
     * Retrieves the chemical mix adjacent in the given direction
     *
     * @param direction the direction of the neighbor
     * @return The adjacent ChemicalMixBlockEntity, or null if it does not exist
     */
    @Nullable
    public ChemicalMixBlockEntity getNeighbor(Direction direction) {
        assert this.level != null;
        BlockPos neighborPos = this.worldPosition.relative(direction);
        BlockEntity neighborUntyped = this.level.getBlockEntity(neighborPos);
        if (neighborUntyped == null) return null;
        if (neighborUntyped.getType() != getTypeInstance()) return null;
        ChemicalMixBlockEntity neighbor = ((ChemicalMixBlockEntity) neighborUntyped);
        if (!neighbor.shouldTick()) return null;
        return ((ChemicalMixBlockEntity) neighborUntyped);
    }

    /**
     * This returns true iff this instance should process chemistry ticks
     */
    public boolean shouldTick() {
        if (!this.hasLevel()) {
            LOGGER.error("ChemicalMixBlockEntity should always have a level");
            return false;
        }
        assert this.level != null;
        if (!this.level.isClientSide) {
            ChunkAccess chunkAccess = this.level.getChunk(this.worldPosition);
            if (chunkAccess instanceof LevelChunk levelChunk) {
                ServerLevel serverLevel = ((ServerLevel) levelChunk.getLevel());
                boolean validStatus = levelChunk.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING);
                boolean loadedProperly = serverLevel.areEntitiesLoaded(ChunkPos.asLong(this.worldPosition));
                return validStatus && loadedProperly;
            }
        }
        return true;
    }

    public Vector4fc getColor() {
        Vector3f totalColor = new Vector3f();
        float absorbance = 0;
        for (Map.Entry<Chemical, Double> entry : this.contents.getComponents().entrySet()) {
            Chemical chemical = entry.getKey();
            double concentration = entry.getValue() / BLOCK_VOLUME;
            Vector4fc defaultColor = chemical.getColor();
            Vector3f defaultOpaqueColor = new Vector3f(defaultColor.x(), defaultColor.y(), defaultColor.z());
            totalColor = totalColor.add(defaultOpaqueColor.mul((float) concentration));
            absorbance += (float) (chemical.getColor().w() * concentration);
        }
        if(absorbance < 0.001f){
            return new Vector4f(0);
        }
        Vector3f averageColor = totalColor.div(absorbance);
        float opacity = (float)(1.0 - Math.exp(-absorbance));
        return new Vector4f(averageColor, opacity);
    }

    public double getTemperature(){
        return this.heat / this.contents.getHeatCapacity();
    }

    @SubscribeEvent
    public void onChemTick(ChemicalTickEvent tick) {
        // Validate that tick is intended for this BlockEntity
        if (this.isRemoved()) {
            MinecraftForge.EVENT_BUS.unregister(this);
            return;
        }
        if (tick.level != this.level) {
            return;
        }

        if (!this.shouldTick()) {
            return;
        }
        assert this.level != null;

        this.setChanged();

        int neighborCount = 0;
        for (Direction direction : Direction.values()) {
            if (getNeighbor(direction) != null) neighborCount++;
        }

        internalTick();
    }

    private void internalTick(){
        if(this.contents.getTotalAmount() == 0) return;
        double temperature = getTemperature();
        for (ChemicalReaction reaction : this.getReactionList()) {
            Map<Chemical, Integer> inputs = reaction.getInputs();
            double inputProduct = 1;
            for (Map.Entry<Chemical, Integer> inputEntry : inputs.entrySet()) {
                Chemical chemical = inputEntry.getKey();
                int coefficient = inputEntry.getValue();
                double concentration = this.contents.getAmount(chemical) / BLOCK_VOLUME;
                inputProduct *= Math.pow(concentration, coefficient);
            }
            double rate = inputProduct * reaction.getRateConstant();

            Map<Chemical, Integer> outputs = reaction.getOutputs();
            double outputProduct = 1;
            for(Map.Entry<Chemical, Integer> outputEntry : outputs.entrySet()){
                Chemical chemical = outputEntry.getKey();
                int coefficient = outputEntry.getValue();
                double concentration = this.contents.getAmount(chemical) / BLOCK_VOLUME;
                outputProduct *= Math.pow(concentration, coefficient);
            }
            double invKeq = Math.exp(-reaction.getEntropyChange(temperature) / 8.314);
            LOGGER.warn("K eq = {}", 1.0 / invKeq);
            rate -= outputProduct * reaction.getRateConstant() * invKeq;


            double dt = 0.05;

            this.contents = this.contents.add(reaction.getMolOutput().scale(rate * dt));
            this.contents = this.contents.sub(reaction.getMolInput().scale(rate * dt));
            this.heat -= rate * dt * reaction.getEnergyChange(temperature);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("contents", contents.serializeNBT());
        tag.putDouble("heat", heat);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        contents = new Mixture(tag.getCompound("contents"));
        heat = tag.getDouble("heat");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
