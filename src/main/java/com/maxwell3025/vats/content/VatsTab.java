package com.maxwell3025.vats.content;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class VatsTab {
    private static CreativeModeTab instance;

    public static CreativeModeTab getInstance() {
        if (instance == null) {
            instance = CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vats"))
                    .icon(() -> new ItemStack(CanisterItem.getInstance()))
                    .displayItems((params, output) -> {
                        output.accept(ChemicalMixItem.getInstance());
                        output.accept(CanisterItem.getInstance());
                        output.accept(CreativeMeterItem.getInstance());
                    })
                    .build();
        }
        return instance;
    }
}
