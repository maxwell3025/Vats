package com.maxwell3025.vats.api;

import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Chemical extends ForgeRegistryEntry<Chemical> {
    public Chemical(){

    }
    public double mbPerMole(){
        return 1;
    }
}
