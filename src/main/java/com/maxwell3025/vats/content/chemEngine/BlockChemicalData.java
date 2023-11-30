package com.maxwell3025.vats.content.chemEngine;

import com.maxwell3025.vats.api.Mixture;
import net.minecraft.nbt.CompoundTag;

public class BlockChemicalData {
    Mixture gasses = new Mixture();
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
                '}';
    }
}
