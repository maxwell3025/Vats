package com.maxwell3025.vats.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CanisterItem extends Item {
    public CanisterItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        //check if clicked on vat
        if(blockEntity instanceof VatBlockEntity vatEntity){

        }
        return InteractionResult.PASS;
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
        return 1;
    }
}
