package com.maxwell3025.vats.content;

import net.minecraft.world.item.CreativeModeTab;

public class VatsTab extends CreativeModeTab {
    private static VatsTab instance;
    public static VatsTab getInstance(){
        if(instance == null){
             instance = new VatsTab("vats");
        }
        return instance;
    }
    private VatsTab(String label) {
        super(CreativeModeTab.builder()
            .displayItems((params, output) -> {
                output.accept(ChemicalMixItem.getInstance());
                output.accept(CanisterItem.getInstance());
                output.accept(CreativeMeterItem.getInstance());
            })
        );
    }
}
