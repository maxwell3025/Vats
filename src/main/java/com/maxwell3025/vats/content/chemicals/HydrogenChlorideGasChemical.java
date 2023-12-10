package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;

public class HydrogenChlorideGasChemical extends Chemical {
    private static HydrogenChlorideGasChemical instance;
    public static HydrogenChlorideGasChemical getInstance(){
        if(instance == null){
            instance = new HydrogenChlorideGasChemical();
        }
        return instance;
    }
    @Override
    public double getEntropy(double temperature) {
        return 0;
    }
    private HydrogenChlorideGasChemical(){
        super();
    }
}
