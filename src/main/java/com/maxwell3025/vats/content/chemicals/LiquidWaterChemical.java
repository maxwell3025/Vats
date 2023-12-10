package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;

public class LiquidWaterChemical extends Chemical {
    private static LiquidWaterChemical instance = null;
    public static LiquidWaterChemical getInstance(){
        if(instance == null){
            instance = new LiquidWaterChemical();
        }
        return instance;
    }
    private LiquidWaterChemical(){
        super();
    }

    @Override
    public double getEntropy(double temperature) {
        return 0;
    }
}
