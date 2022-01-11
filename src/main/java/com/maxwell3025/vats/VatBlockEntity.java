package com.maxwell3025.vats;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VatBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private int clickTimes = 0;
    public VatBlockEntity(BlockPos pos, BlockState state) {
        super(AnnotatedHolder.BLOCKENTITYVAT, pos, state);
    }
    public void increment(){
        clickTimes++;
    }
    public int getClickTimes(){
        return clickTimes;
    }
}
