package com.maxwell3025.vats.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CanisterItem extends Item {
    private static CanisterItem instance = null;
    public static CanisterItem getInstance(){
        if(instance == null){
            instance = new CanisterItem(new Item.Properties().stacksTo(1));
        }
        return instance;
    }
    private CanisterItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(level.getBlockState(pos).is(ChemicalMixBlock.getInstance())){
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
