package com.maxwell3025.vats;


import com.maxwell3025.vats.holders.VatBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.IForgeRegistry;

public class RegisterHandler {
    public static void registerBlocks(IForgeRegistry<Block> register){
        register.register(new VatBlock(BlockBehaviour.Properties.of(Material.STONE)).setRegistryName("vat"));
    }
    public static void registerItems(IForgeRegistry<Item> register){
        register.register(new VatItem(VatBlocks.VAT, new Item.Properties()).setRegistryName("vat"));
    }
    public static void registerBlockEntities(IForgeRegistry<BlockEntityType<?>> register){
        BlockEntityType<VatBlockEntity> vatBlockEntity = BlockEntityType.Builder.of(VatBlockEntity::new, VatBlocks.VAT).build(null);
        register.register(vatBlockEntity.setRegistryName("vat"));
    }
}
