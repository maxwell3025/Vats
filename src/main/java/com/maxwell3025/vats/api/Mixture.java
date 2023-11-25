package com.maxwell3025.vats.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.maxwell3025.vats.Vats.MODID;
import static com.maxwell3025.vats.api.RegisterHandler.CHEMICAL_REGISTRY;

public class Mixture {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, Integer> components = new HashMap<>();
    private int amount = 0;

    public Mixture() {
    }

    public Mixture(Chemical chemical, int amount) {
        String registryKey = CHEMICAL_REGISTRY.get().getKey(chemical).getPath();
        components.put(registryKey, amount);
        this.amount = amount;
    }

    public Mixture(CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            components.put(key, tag.getInt(key));
            amount += tag.getDouble(key);
        }
    }

    public void add(Mixture other){
        for (Map.Entry<String, Integer> pair : other.components.entrySet()) {
            int currentAmount = components.getOrDefault(pair.getKey(), 0);
            int addedAmount = other.components.get(pair.getKey());
            components.put(pair.getKey(), currentAmount+addedAmount);
        }
        amount += other.amount;
    }
    public CompoundTag save(CompoundTag tag) {
        for (Map.Entry<String, Integer> entry : components.entrySet()) {
            tag.putDouble(entry.getKey(), entry.getValue());
        }
        return tag;
    }

    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append(String.format("total amount: %d\n", amount));
        for(Map.Entry<String, Integer> pair : components.entrySet()){
            out.append(String.format("contains %d mL of %s\n", pair.getValue(), pair.getKey()));
        }
        return out.toString().trim();
    }

    public int getAmount(){
        return amount;
    }
}
