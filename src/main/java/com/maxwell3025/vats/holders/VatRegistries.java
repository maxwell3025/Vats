package com.maxwell3025.vats.holders;

import com.maxwell3025.vats.api.Chemical;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class VatRegistries {
    public static final IForgeRegistry<Chemical> CHEMICALS = RegistryManager.ACTIVE.getRegistry(Chemical.class);
}
