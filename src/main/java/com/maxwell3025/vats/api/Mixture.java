package com.maxwell3025.vats.api;

import com.maxwell3025.vats.RegisterHandler;
import com.maxwell3025.vats.holders.VatRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Mixture {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<Chemical, Integer> components = new HashMap<>();
    private int amount = 0;

    public Mixture() {
    }

    public Mixture(Chemical chemical, int amount) {
        components.put(chemical, amount);
        this.amount = amount;
    }

    public Mixture(CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            components.put(VatRegistries.CHEMICALS.getValue(new ResourceLocation(key)), tag.getInt(key));
            amount += tag.getDouble(key);
        }
    }

    public void add(Mixture other){
        for (Map.Entry<Chemical, Integer> pair : other.components.entrySet()) {
            int currentAmount = components.getOrDefault(pair.getKey(), 0);
            int addedAmount = other.components.get(pair.getKey());
            components.put(pair.getKey(), currentAmount+addedAmount);
        }
        amount += other.amount;
    }
    public CompoundTag save(CompoundTag tag) {
        for (Map.Entry<Chemical, Integer> pair : components.entrySet()) {
            tag.putDouble(pair.getKey().getRegistryName().toString(), pair.getValue());
        }
        return tag;
    }

    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append(String.format("total amount: %d\n", amount));
        for(Map.Entry<Chemical, Integer> pair : components.entrySet()){
            out.append(String.format("contains %d mL of %s\n", pair.getValue(), pair.getKey().getRegistryName()));
        }
        return out.toString().trim();
    }

    public int getAmount(){
        return amount;
    }
}
