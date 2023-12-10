package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;

public class HydrogenGasChemical extends Chemical {
    private static HydrogenGasChemical instance = null;
    public static HydrogenGasChemical getInstance(){
        if(instance == null){
            instance = new HydrogenGasChemical();
        }
        return instance;
    }
    @Override
    public double getEntropy(double temperature) {
        return 0;
    }

    @Override
    public String toFormulaString() {
        return "H2";
    }

    private HydrogenGasChemical() {
        super();
    }
}
