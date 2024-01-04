package com.maxwell3025.vats.content;

import com.maxwell3025.vats.RegisterHandler;
import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.api.Mixture;
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
                        output.accept(CreativeMeterItem.getInstance());
                        output.accept(CanisterItem.makeCanister(new Mixture(), 0));
                        for (Chemical chemical : RegisterHandler.CHEMICAL_REGISTRY.get()) {
                            output.accept(CanisterItem.makeCanister(new Mixture(chemical, 1000), 298.15));
                        }
                    })
                    .build();
        }
        return instance;
    }
}
