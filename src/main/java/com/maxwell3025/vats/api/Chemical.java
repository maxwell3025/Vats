package com.maxwell3025.vats.api;

import com.maxwell3025.vats.RegisterHandler;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Chemical {
    private static final Logger LOGGER = LogManager.getLogger();
    public Chemical(){

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
        return key.getPath();
    }
    public abstract double getEntropy(double temperature);

    public abstract String toFormulaString();
}
