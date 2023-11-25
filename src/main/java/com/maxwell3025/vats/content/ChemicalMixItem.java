package com.maxwell3025.vats.content;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ChemicalMixItem extends BlockItem {
    private static ChemicalMixItem instance = null;
    public static ChemicalMixItem getInstance(){
        if(instance == null){
            instance = new ChemicalMixItem(ChemicalMixBlock.getInstance(), new Item.Properties());
        }
        return instance;
    }
    private ChemicalMixItem(Block block, Properties properties) {
        super(block, properties);
    }
}
