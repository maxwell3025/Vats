package com.maxwell3025.vats.api;

import com.maxwell3025.vats.RegisterHandler;
import it.unimi.dsi.fastutil.objects.Object2DoubleMaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a concrete mixture of chemicals with no defined size.
 * For the most part, this is just a data class with some utilities.
 */
public class Mixture {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<Chemical, Double> components = new HashMap<>();

    public Mixture() {
    }

    public Map<Chemical, Double> getComponents(){
        return new HashMap<>(this.components);
    }

    public Mixture(Mixture other) {
        this.components = new HashMap<>(other.components);
    }

    public Mixture(Chemical chemical, double amount) {
        components.put(chemical, amount);
    }

    public Mixture(CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            components.put(Chemical.fromRegistryName(key), tag.getDouble(key));
        }
    }

    public double getAmount(Chemical chemical) {
        return components.getOrDefault(chemical, 0.0);
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (double amount : components.values()) {
            totalAmount += amount;
        }
        return totalAmount;
    }

    public Mixture scale(double rhs) {
        Mixture out = new Mixture();
        for (Map.Entry<Chemical, Double> pair : this.components.entrySet()) {
            double currentAmount = pair.getValue();
            out.components.put(pair.getKey(), currentAmount * rhs);
        }
        return out;
    }

    public Mixture add(Mixture rhs) {
        Mixture out = new Mixture(this);
        for (Map.Entry<Chemical, Double> pair : rhs.components.entrySet()) {
            double currentAmount = out.components.getOrDefault(pair.getKey(), 0.0);
            double addedAmount = rhs.components.get(pair.getKey());
            out.components.put(pair.getKey(), currentAmount + addedAmount);
        }
        return out;
    }

    public Mixture sub(Mixture rhs) {
        return add(rhs.scale(-1));
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Mixture {\n");
        for (Map.Entry<Chemical, Double> pair : components.entrySet()) {
            out.append(String.format("    %8.3f mol %s\n", pair.getValue(), pair.getKey().toFormulaString()));
        }
        out.append("}\n");
        return out.toString();
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<Chemical, Double> entry : components.entrySet()) {
            tag.putDouble(entry.getKey().getRegistryName(), entry.getValue());
        }
        return tag;
    }
}
