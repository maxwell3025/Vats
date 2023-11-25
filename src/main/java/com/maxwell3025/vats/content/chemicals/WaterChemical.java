package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;
import com.maxwell3025.vats.content.CanisterItem;
import net.minecraft.world.item.Item;

public class WaterChemical extends Chemical {
    private static WaterChemical instance = null;
    public static WaterChemical getInstance(){
        if(instance == null){
            instance = new WaterChemical();
        }
        return instance;
    }
    private WaterChemical(){
        super();
    }
}
