package com.maxwell3025.vats.content;

import com.maxwell3025.vats.AnnotatedHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class VatBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private int clickTimes = 0;
    public VatBlockEntity(BlockPos pos, BlockState state) {
        super(AnnotatedHolder.BLOCKENTITYVAT, pos, state);
    }
    public void increment(){
        clickTimes++;
        this.setChanged();
    }
    public int getClickTimes(){
        return clickTimes;
    }
    @Override
    public void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putInt("clickTimes",clickTimes);
    }
    @Override
    public void load(CompoundTag tag){
        super.load(tag);
        clickTimes = tag.getInt("clickTimes");
    }
}
