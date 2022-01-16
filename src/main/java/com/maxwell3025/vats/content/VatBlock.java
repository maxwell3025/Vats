package com.maxwell3025.vats.content;

import com.maxwell3025.vats.api.Mixture;
import com.maxwell3025.vats.holders.VatChemicals;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VatBlock extends BaseEntityBlock {
    private static final Logger LOGGER = LogManager.getLogger();
    public VatBlock(Properties properties) {
        super(properties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VatBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof VatBlockEntity vatBlockEntity) {
            ItemStack handItems = player.getItemInHand(hand);
            if(handItems.getItem() == Items.WATER_BUCKET){
                double amountUsed = vatBlockEntity.addMixture(new Mixture(VatChemicals.WATER, 1000), true);
                if(amountUsed>0){
                    if(!player.isCreative()) player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.FAIL;
            }
            if(handItems.getItem() == Items.DEBUG_STICK && level.isClientSide) {
                player.sendMessage(new TextComponent(vatBlockEntity.getMixture().toString()), player.getUUID());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
