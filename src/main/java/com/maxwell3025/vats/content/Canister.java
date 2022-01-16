package com.maxwell3025.vats.content;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class Canister extends Item {
    public Canister(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getLevel().isClientSide){
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

}
