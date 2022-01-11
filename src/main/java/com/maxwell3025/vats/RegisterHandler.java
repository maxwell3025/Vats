package com.maxwell3025.vats;


import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.content.VatBlock;
import com.maxwell3025.vats.content.VatBlockEntity;
import com.maxwell3025.vats.content.VatItem;
import com.maxwell3025.vats.content.chemicals.WaterChemical;
import com.maxwell3025.vats.holders.VatBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void newRegister(){
        new RegistryBuilder<Chemical>().setType(Chemical.class).setName(new ResourceLocation("vats", "chemical")).create();
    }

    public static void registerBlocks(IForgeRegistry<Block> register){
        LOGGER.info("block registry: "+register.getRegistryName());
        register.register(new VatBlock(BlockBehaviour.Properties.of(Material.STONE)).setRegistryName("vat"));
    }
    public static void registerItems(IForgeRegistry<Item> register){
        register.register(new VatItem(VatBlocks.VAT, new Item.Properties()).setRegistryName("vat"));
    }
    public static void registerBlockEntities(IForgeRegistry<BlockEntityType<?>> register){
        BlockEntityType<VatBlockEntity> vatBlockEntity = BlockEntityType.Builder.of(VatBlockEntity::new, VatBlocks.VAT).build(null);
        register.register(vatBlockEntity.setRegistryName("vat"));
    }
    public static void registerChemicals(IForgeRegistry<Chemical> register){
        LOGGER.info("chemical registry: "+register.getRegistryName());
        register.register(new WaterChemical().setRegistryName("water"));
    }
}
