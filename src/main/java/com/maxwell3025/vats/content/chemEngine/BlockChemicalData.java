package com.maxwell3025.vats.content.chemEngine;

import com.maxwell3025.vats.api.Chemical;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class BlockChemicalData {
    Map<Chemical, Double> gasses = new HashMap<>();
    Map<Chemical, Double> liquids = new HashMap<>();
    public BlockChemicalData(){}
    public BlockChemicalData(CompoundTag tag){

    }
    public CompoundTag serializeNBT(){
        return new CompoundTag();
    }
    public void internalReaction(double dt){

    }

    @Override
    public String toString() {
        return "BlockChemicalData{" +
                "gasses=" + gasses +
                ", liquids=" + liquids +
                '}';
    }
}
