package com.maxwell3025.vats.content;

import com.maxwell3025.vats.RegisterHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativeMeterItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();
    private static CreativeMeterItem instance = null;

    public static CreativeMeterItem getInstance() {
        if (instance == null) {
            instance = new CreativeMeterItem(new Item.Properties());
        }
        return instance;
    }

    public CreativeMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos position = context.getClickedPos();
        BlockEntity blockEntity = context.getLevel().getBlockEntity(position);
        if (
                blockEntity instanceof ChemicalMixBlockEntity chemicalMixBlockEntity &&
                        !context.getLevel().isClientSide &&
                        context.getPlayer() != null
        ) {
            context.getPlayer().sendSystemMessage(Component.literal(chemicalMixBlockEntity.getReactionList().toString()));
            context.getPlayer().sendSystemMessage(Component.literal(chemicalMixBlockEntity.getContents().toString()));
            context.getPlayer().sendSystemMessage(Component.literal(chemicalMixBlockEntity.getColor().toString()));
        }
        return InteractionResult.SUCCESS;
    }
}
