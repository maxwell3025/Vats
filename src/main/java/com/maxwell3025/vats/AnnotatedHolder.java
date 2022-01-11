package com.maxwell3025.vats;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("vats")
public class AnnotatedHolder {
    @ObjectHolder("vat")
    public static final BlockEntityType<VatBlockEntity> BLOCKENTITYVAT = null;
}
