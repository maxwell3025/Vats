package com.maxwell3025.vats.content.chemEngine;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChemicalEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<ResourceKey<Level>, Set<ChunkPos>> loadedChunks = new HashMap<>();
    private static HashMap<ResourceKey<Level>, Integer> tickNumber = new HashMap<>();
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event){
        event.register(ChunkChemicalData.class);
    }
    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<LevelChunk> event){
        LevelChunk chunk = event.getObject();
        Capability<ChunkChemicalData> CHEMICAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
        ChunkChemicalData chunkData = new ChunkChemicalData(chunk);
        ICapabilityProvider provider = new ICapabilitySerializable<CompoundTag>() {
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction direction) {
                if (cap == CHEMICAL_CAPABILITY) {
                    return LazyOptional.of(() -> chunkData).cast();
                }
                return LazyOptional.empty();
            }

            @Override
            public CompoundTag serializeNBT() {
                return chunkData.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundTag tag) {
                chunkData.deserializeNBT(tag);
            }
        };
        event.addCapability(new ResourceLocation("vats", "world_chemicals"), provider);
    }
    static int phase = 0;
    @SubscribeEvent
    public static void chemTick(TickEvent.LevelTickEvent event){
        Capability<ChunkChemicalData> CHEMICAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
        if(event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER){
            ResourceKey<Level> dimension = event.level.dimension();
            int tickNum = tickNumber.getOrDefault(dimension, 0);
            tickNum = (tickNum + 1) % 100;
            tickNumber.put(dimension, tickNum);
            if(tickNum == 0) {
                loadedChunks.getOrDefault(dimension, new HashSet<>()).forEach(chunkPos -> {
                    // LOGGER.warn("Chunk update at " + chunkPos.toString() + ", " + dimension);
                    if(!event.level.hasChunk(chunkPos.x, chunkPos.z)){
                        LOGGER.error("Nonexistent chunk cached");
                    }
                    LevelChunk chunk = event.level.getChunkAt(chunkPos.getWorldPosition());
                    chunk.getCapability(CHEMICAL_CAPABILITY).ifPresent(data -> {
                        data.internalReaction(0.01);
                    });
                });
            }
        }
    }
    @SubscribeEvent
    public static void onLoad(ChunkEvent.Load event){
        if(event.getLevel().isClientSide()) return;
        ResourceKey<Level> dimension = ((Level)event.getLevel()).dimension();

        Set<ChunkPos> levelSet = loadedChunks.computeIfAbsent(dimension, _key -> new HashSet<>());
        ChunkPos pos = event.getChunk().getPos();
        if(levelSet.contains(pos)){
            LOGGER.warn("Redundant load at " + pos);
        }
        levelSet.add(pos);
        LOGGER.warn("# of chunks loaded: " + loadedChunks.get(dimension).size());
    }
    @SubscribeEvent
    public static void onUnload(ChunkEvent.Unload  event){

        if(event.getLevel().isClientSide()) return;
        ResourceKey<Level> dimension = ((Level)event.getLevel()).dimension();
        Set<ChunkPos> levelSet = loadedChunks.get(dimension);
        ChunkPos pos = event.getChunk().getPos();
        if(!levelSet.contains(pos)){
            LOGGER.warn("Illegal unload at " + pos);
        }
        levelSet.remove(event.getChunk().getPos());
        LOGGER.warn("# of chunks loaded: " + loadedChunks.get(dimension).size());
    }
}
