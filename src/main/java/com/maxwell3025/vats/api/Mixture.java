package com.maxwell3025.vats.api;

import com.maxwell3025.vats.RegisterHandler;
import net.minecraft.nbt.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Mixture {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, Double> components = new HashMap<>();

    public Mixture() {}
    public Mixture(Mixture other) {
        this.components = new HashMap<>(other.components);
    }
    public Mixture(Chemical chemical, double amount) {
        String registryKey = RegisterHandler.CHEMICAL_REGISTRY.get().getKey(chemical).getPath();
        components.put(registryKey, amount);
    }

    public Mixture(CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            components.put(key, tag.getDouble(key));
        }
    }
    public Mixture scale(double rhs){
        Mixture out = new Mixture();
        for (Map.Entry<String, Double> pair : this.components.entrySet()) {
            double currentAmount = pair.getValue();
            out.components.put(pair.getKey(), currentAmount * rhs);
        }
        return out;
    }
    public Mixture add(Mixture rhs){
        Mixture out = new Mixture(this);
        for (Map.Entry<String, Double> pair : rhs.components.entrySet()) {
            double currentAmount = out.components.getOrDefault(pair.getKey(), 0.0);
            double addedAmount = rhs.components.get(pair.getKey());
            out.components.put(pair.getKey(), currentAmount+addedAmount);
        }
        return out;
    }
    public CompoundTag save(CompoundTag tag) {
        for (Map.Entry<String, Double> entry : components.entrySet()) {
            tag.putDouble(entry.getKey(), entry.getValue());
        }
        return tag;
    }

    public String toString(){
        StringBuilder out = new StringBuilder();
        out.append("Mixture {\n");
        for(Map.Entry<String, Double> pair : components.entrySet()){
            out.append(String.format("\t%8.3f %s\n", pair.getValue(), pair.getKey()));
        }
        out.append("}\n");
        return out.toString();
    }
}
