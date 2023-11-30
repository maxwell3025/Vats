package com.maxwell3025.vats.content.chemEngine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChunkChemicalData {
    private static final Logger LOGGER = LogManager.getLogger();
    public static Map<ResourceKey<Level>, Map<ChunkPos, ChunkChemicalData>> chemicalMap = new HashMap<>();
    private LevelChunk chunk;
    public Map<BlockPos, BlockChemicalData> concentrations = new HashMap<>();
    public ChunkChemicalData(LevelChunk chunk){
        this.chunk = chunk;
        ResourceKey<Level> dimension = this.chunk.getLevel().dimension();
        Map<ChunkPos, ChunkChemicalData> levelChemicalMap = chemicalMap.computeIfAbsent(dimension, _dim -> new HashMap<>());
        levelChemicalMap.put(this.chunk.getPos(), this);
    }
    public CompoundTag serializeNBT(){
        CompoundTag nbt = new CompoundTag();
        ListTag data = new ListTag();
        for(Map.Entry<BlockPos, BlockChemicalData> blockInfo: this.concentrations.entrySet()){
            CompoundTag listEntry = new CompoundTag();
            BlockPos position = blockInfo.getKey();
            BlockChemicalData blockData = blockInfo.getValue();
            CompoundTag blockDataTag = blockData.serializeNBT();
            listEntry.put("blockData", blockDataTag);
            listEntry.put("x", IntTag.valueOf(position.getX()));
            listEntry.put("y", IntTag.valueOf(position.getY()));
            listEntry.put("z", IntTag.valueOf(position.getZ()));
        }
        nbt.put("data", data);
        return nbt;
    }
    public void deserializeNBT(CompoundTag tag){
        ListTag data = (ListTag)tag.get("data");
        if(data == null){
            return;
        }
        for(Tag blockDataUncast: data){
            CompoundTag blockData = (CompoundTag) blockDataUncast;

            IntTag xTag = (IntTag) blockData.get("x");
            IntTag yTag = (IntTag) blockData.get("y");
            IntTag zTag = (IntTag) blockData.get("z");
            CompoundTag blockDataTag = (CompoundTag) blockData.get("blockData");
            if(xTag == null || yTag == null || zTag == null || blockDataTag == null){
                LOGGER.error("Chemical block tag missing elements");
                return;
            }

            int x = ((IntTag) Objects.requireNonNull(blockData.get("x"))).getAsInt();
            int y = ((IntTag) Objects.requireNonNull(blockData.get("y"))).getAsInt();
            int z = ((IntTag) Objects.requireNonNull(blockData.get("z"))).getAsInt();

            BlockPos position = new BlockPos(x, y, z);

            this.concentrations.put(
                position,
                new BlockChemicalData(blockDataTag)
            );
        }
    }
    public void internalReaction(double dt){
        chunk.setUnsaved(true);
        concentrations.values().forEach(blockChemicalData -> blockChemicalData.internalReaction(dt));
    }
    public void diffuse(){
        chunk.setUnsaved(true);
        for(Map.Entry<BlockPos, BlockChemicalData> entry: concentrations.entrySet()){

        }
    }

    @Override
    public String toString() {
        return "ChunkChemicalData{" +
                "concentrations=" + concentrations +
                '}';
    }
}
