package com.maxwell3025.vats.content;

import com.maxwell3025.vats.api.Mixture;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@MethodsReturnNonnullByDefault
public class CanisterItem extends Item {
    private static final Logger LOGGER = LogManager.getLogger();
    private static CanisterItem instance = null;

    public static final double CANISTER_MAXIMUM_CAPACITY = 1000;

    public static CanisterItem getInstance() {
        if (instance == null) {
            instance = new CanisterItem(new Item.Properties().stacksTo(1));
        }
        return instance;
    }

    private CanisterItem(Properties p_41383_) {
        super(p_41383_.defaultDurability(1000));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> currentToolTip, TooltipFlag flag) {
        if (flag.isAdvanced()) {
            String[] tooltipMessage = getContents(stack).toString().split("\n");
            for (String line : tooltipMessage) {
                currentToolTip.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (level.getBlockEntity(pos) instanceof ChemicalMixBlockEntity blockEntity) {
            blockEntity.setChanged();
            ItemStack canisterItemStack = context.getItemInHand();
            Mixture canisterContents = getContents(canisterItemStack);
            if (context.isSecondaryUseActive()) {
                // Insert chemical if shifting
                blockEntity.addContents(canisterContents);
                if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                    setContents(canisterItemStack, new Mixture());
                }
            } else {
                // Take chemical otherwise
                Mixture originalBlockEntityContents = blockEntity.getContents();
                double remainingCapacity = CANISTER_MAXIMUM_CAPACITY - canisterContents.getTotalAmount();
                double blockEntityFillAmount = blockEntity.getContents().getTotalAmount();
                Mixture transferredMix;
                if(remainingCapacity >= blockEntityFillAmount){
                    transferredMix = originalBlockEntityContents;
                    blockEntity.setContents(new Mixture());
                } else {
                    double amountRemoved = Math.min(remainingCapacity, blockEntityFillAmount);
                    double fractionRemoved = amountRemoved / blockEntityFillAmount;
                    transferredMix = blockEntity.getContents().scale(fractionRemoved);
                    blockEntity.setContents(blockEntity.getContents().sub(transferredMix));
                }

                if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                    setContents(canisterItemStack, canisterContents.add(transferredMix));
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void setContents(ItemStack canisterItemStack, Mixture contents) {
        if (canisterItemStack.getItem() != getInstance()) {
            LOGGER.error("Attempted to set chemical contents of non-canister item stack");
            return;
        }
        double totalAmount = contents.getTotalAmount();
        if (totalAmount > CANISTER_MAXIMUM_CAPACITY) {
            LOGGER.error("Attempted to set chemical contents of canister item stack to more than it's capacity");
            return;
        }
        canisterItemStack.setDamageValue(100 - (int) Math.floor(totalAmount / CANISTER_MAXIMUM_CAPACITY * 100));
        CompoundTag itemStackTag = canisterItemStack.getOrCreateTag();
        itemStackTag.put("contents", contents.serializeNBT());
        canisterItemStack.setTag(itemStackTag);
    }

    public static Mixture getContents(ItemStack canisterItemStack) {
        if (canisterItemStack.getItem() != getInstance()) {
            LOGGER.error("Attempted to get chemical contents of non-canister item stack");
            return new Mixture();
        }
        CompoundTag itemStackTag = canisterItemStack.getOrCreateTag();
        CompoundTag contentsTag = itemStackTag.getCompound("contents");
        return new Mixture(contentsTag);
    }

    public static ItemStack makeCanister(Mixture contents) {
        ItemStack canisterItemStack = new ItemStack(CanisterItem.getInstance());
        setContents(canisterItemStack, contents);
        return canisterItemStack;
    }
}
