package com.maxwell3025.vats.content;

import com.maxwell3025.vats.AnnotatedHolder;
import com.maxwell3025.vats.api.Mixture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VatBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private Mixture mixture = new Mixture();
    private final double capacity = 1000;

    public Mixture getMixture() {
        return mixture;
    }

    public VatBlockEntity(BlockPos pos, BlockState state) {
        super(AnnotatedHolder.BLOCKENTITYVAT, pos, state);
    }

    public double addMixture(Mixture addedMixture, boolean discrete) {
        if (discrete) {
            if (addedMixture.getAmount() + mixture.getAmount() > capacity) {
                return 0;
            }else{
                mixture.add(addedMixture);
                return 1;
            }
        }else{
            //TODO implement varaible mixture amount addition:eg from a tank or something that has a continuous amount
        }
        this.setChanged();
        return 0;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag mixtureTag = new CompoundTag();
        mixture.save(mixtureTag);
        tag.put("mixture", mixtureTag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag contentTag = tag.getCompound("mixture");
        mixture = new Mixture(contentTag);
    }
}
