package com.maxwell3025.vats.content;

import com.maxwell3025.vats.api.Mixture;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
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
            currentToolTip.add(Component.literal(String.valueOf(getHeat(stack))).withStyle(ChatFormatting.GRAY));
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
                blockEntity.setHeat(blockEntity.getHeat() + getHeat(canisterItemStack));
                if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                    setContents(canisterItemStack, new Mixture());
                    setHeat(canisterItemStack, 0);
                }
            } else {
                // Take chemical otherwise
                Mixture originalBlockEntityContents = blockEntity.getContents();
                double remainingCapacity = CANISTER_MAXIMUM_CAPACITY - canisterContents.getTotalAmount();
                double blockEntityFillAmount = blockEntity.getContents().getTotalAmount();
                Mixture transferredMix;
                double transferredHeat;
                if (remainingCapacity >= blockEntityFillAmount) {
                    transferredMix = originalBlockEntityContents;
                    transferredHeat = blockEntity.getHeat();
                    blockEntity.setContents(new Mixture());
                    blockEntity.setHeat(0);
                } else {
                    double amountRemoved = Math.min(remainingCapacity, blockEntityFillAmount);
                    double fractionRemoved = amountRemoved / blockEntityFillAmount;
                    transferredHeat = blockEntity.getHeat() * fractionRemoved;
                    transferredMix = blockEntity.getContents().scale(fractionRemoved);
                    blockEntity.setContents(blockEntity.getContents().sub(transferredMix));
                    blockEntity.setHeat(blockEntity.getHeat() - transferredHeat);
                }

                if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
                    setContents(canisterItemStack, canisterContents.add(transferredMix));
                    setHeat(canisterItemStack, getHeat(canisterItemStack) + transferredHeat);
                }
            }
            level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static void setHeat(ItemStack canisterItemStack, double contents) {
        if (canisterItemStack.getItem() != getInstance()) {
            LOGGER.error("Attempted to set heat of non-canister item stack");
            return;
        }
        CompoundTag itemStackTag = canisterItemStack.getOrCreateTag();
        itemStackTag.putDouble("heat", contents);
    }

    public static double getHeat(ItemStack canisterItemStack) {
        if (canisterItemStack.getItem() != getInstance()) {
            LOGGER.error("Attempted to set heat of non-canister item stack");
            return 0;
        }
        CompoundTag itemStackTag = canisterItemStack.getOrCreateTag();
        if (!itemStackTag.contains("heat")) itemStackTag.putDouble("heat", 0);
        return itemStackTag.getDouble("heat");
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

    public static ItemStack makeCanister(Mixture contents, double temperature) {
        ItemStack canisterItemStack = new ItemStack(CanisterItem.getInstance());
        setContents(canisterItemStack, contents);
        setHeat(canisterItemStack, contents.getHeatCapacity() * temperature);
        return canisterItemStack;
    }
}
