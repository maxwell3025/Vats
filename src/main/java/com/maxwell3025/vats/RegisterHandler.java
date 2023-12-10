package com.maxwell3025.vats;


import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.api.ChemicalReaction;
import com.maxwell3025.vats.api.ChemicalReactionSerializer;
import com.maxwell3025.vats.content.*;
import com.maxwell3025.vats.content.chemicals.ChlorineGasChemical;
import com.maxwell3025.vats.content.chemicals.HydrogenChlorideGasChemical;
import com.maxwell3025.vats.content.chemicals.HydrogenGasChemical;
import com.maxwell3025.vats.content.chemicals.WaterLiquidChemical;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    private static final DeferredRegister<Chemical> CHEMICALS = DeferredRegister.create(new ResourceLocation(MODID, "chemical"), MODID);
    public static final Supplier<IForgeRegistry<Chemical>> CHEMICAL_REGISTRY = CHEMICALS.makeRegistry(RegistryBuilder::new);
    public static final RegistryObject<Block> CHEMICAL_MIX_BLOCK = BLOCKS.register("chemical_mix", ChemicalMixBlock::getInstance);
    public static final RegistryObject<Item> CHEMICAL_MIX_ITEM = ITEMS.register("chemical_mix", ChemicalMixItem::getInstance);
    public static final RegistryObject<Item> CANISTER_ITEM = ITEMS.register("canister", CanisterItem::getInstance);
    public static final RegistryObject<Item> CREATIVE_METER_ITEM = ITEMS.register("creative_meter", CreativeMeterItem::getInstance);
    public static final RegistryObject<CreativeModeTab> VATS_TAB = TABS.register("vats", VatsTab::getInstance);
    public static final RegistryObject<BlockEntityType<ChemicalMixBlockEntity>> MY_BE = BLOCK_ENTITIES.register("chemical_mix", ChemicalMixBlockEntity::getTypeInstance);
    public static final RegistryObject<RecipeType<ChemicalReaction>> CHEMICAL_REACTION = RECIPE_TYPES.register("reaction", ChemicalReaction::getTypeInstance);
    public static final RegistryObject<RecipeSerializer<ChemicalReaction>> CHEMICAL_REACTION_SERIALIZER = RECIPE_SERIALIZERS.register("reaction", ChemicalReactionSerializer::getInstance);
    public static final RegistryObject<Chemical> WATER_LIQUID_CHEMICAL = CHEMICALS.register("water_liquid", WaterLiquidChemical::getInstance);
    public static final RegistryObject<Chemical> CHLORINE_GAS_CHEMICAL = CHEMICALS.register("chlorine_gas", ChlorineGasChemical::getInstance);
    public static final RegistryObject<Chemical> HYDROGEN_GAS_CHEMICAL = CHEMICALS.register("hydrogen_gas", HydrogenGasChemical::getInstance);
    public static final RegistryObject<Chemical> HYDROGEN_CHLORIDE_GAS_CHEMICAL = CHEMICALS.register("hydrogen_chloride_gas", HydrogenChlorideGasChemical::getInstance);

    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CHEMICALS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
