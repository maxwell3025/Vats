package com.maxwell3025.vats.api;

import com.maxwell3025.vats.RegisterHandler;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Chemical {
    private static final Logger LOGGER = LogManager.getLogger();

    public Chemical(){

    }
    public static Chemical fromRegistryName(String key){
        return fromRegistryName(new ResourceLocation(key));
    }
    public static Chemical fromRegistryName(ResourceLocation key){
        return RegisterHandler.CHEMICAL_REGISTRY.get().getValue(key);
    }
    public String getRegistryName(){
        ResourceLocation key = RegisterHandler.CHEMICAL_REGISTRY.get().getKey(this);
        if(key == null){
            LOGGER.error("Chemical " + this.getClass().getName() + " could not be loaded from the registry, " +
                    "meaning that it was likely not loaded properly. Please contact the mod developer");
            return "";
        }
        return key.toString();
    }

    /**
     * This is the molar heat capacity in J / (mol * K)
     */
    public abstract double getHeatCapacity();

    /**
     * This is the hypothetical entropy at J / (mol * K)
     */
    public abstract double getStandardEntropy();

    /**
     * This is the hypothetical molar energy at 0K in J / mol
     */
    public abstract double getStandardEnthalpy();

    /**
     * Calculates the molar entropy of a substance at a given temperature
     * @param temperature temperature in Kelvin
     * @return molar entropy in J / (mol * K)
     */
    public double getEntropy(double temperature){
        return getStandardEntropy() + getHeatCapacity() * Math.log(temperature);
    }

    /**
     * Calculates the molar entropy of a substance at a given temperature
     * @param temperature temperature in Kelvin
     * @return molar energy in J / mol
     */
    public double getEnergy(double temperature){
        return getStandardEnthalpy() + getHeatCapacity() * temperature;
    }

    public abstract String toFormulaString();
}
