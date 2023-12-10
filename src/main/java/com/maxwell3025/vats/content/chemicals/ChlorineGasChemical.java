package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;

public class ChlorineGasChemical extends Chemical {
    private static ChlorineGasChemical instance;
    public static ChlorineGasChemical getInstance(){
        if(instance == null){
            instance = new ChlorineGasChemical();
        }
        return instance;
    }
    @Override
    public double getEntropy(double temperature) {
        return 0;
    }
    private ChlorineGasChemical(){
        super();
    }
}
