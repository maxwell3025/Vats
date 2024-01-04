package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;

public class WaterLiquidChemical extends Chemical {
    private static WaterLiquidChemical instance = null;
    public static WaterLiquidChemical getInstance(){
        if(instance == null){
            instance = new WaterLiquidChemical();
        }
        return instance;
    }
    private WaterLiquidChemical(){
        super();
    }

    @Override
    public double getHeatCapacity() {
        return 75;
    }

    @Override
    public double getStandardEntropy() {
        return 0;
    }

    @Override
    public double getStandardEnthalpy() {
        return -286000;
    }

    @Override
    public double getEntropy(double temperature) {
        return 0;
    }

    @Override
    public String toFormulaString() {
        return "H2O";
    }
}
