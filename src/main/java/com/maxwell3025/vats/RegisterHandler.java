package com.maxwell3025.vats;


import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.content.*;
import com.maxwell3025.vats.content.chemicals.WaterChemical;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

import static com.maxwell3025.vats.Vats.MODID;

public class RegisterHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    private static final DeferredRegister<Chemical> CHEMICALS = DeferredRegister.create(new ResourceLocation(MODID, "chemical"), MODID);
    public static final Supplier<IForgeRegistry<Chemical>> CHEMICAL_REGISTRY = CHEMICALS.makeRegistry(RegistryBuilder::new);
    public static final RegistryObject<Block> CHEMICAL_MIX_BLOCK = BLOCKS.register("chemical_mix", ChemicalMixBlock::getInstance);
    public static final RegistryObject<Item> CHEMICAL_MIX_ITEM = ITEMS.register("chemical_mix", ChemicalMixItem::getInstance);
    public static final RegistryObject<Item> CANISTER_ITEM = ITEMS.register("canister", CanisterItem::getInstance);
    public static final RegistryObject<Item> CREATIVE_METER_ITEM = ITEMS.register("creative_meter", CreativeMeterItem::getInstance);
    public static final RegistryObject<CreativeModeTab> VATS_TAB = TABS.register("vats", VatsTab::getInstance);
    public static final RegistryObject<Chemical> WATER_CHEMICAL = CHEMICALS.register("water", WaterChemical::getInstance);

    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CHEMICALS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
