package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;
import org.joml.Vector4f;
import org.joml.Vector4fc;

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

    @Override
    public Vector4fc getColor() {
        return new Vector4f(0.5f, 1, 0, 0.25f);
    }

    @Override
    public String toFormulaString() {
        return "Cl2";
    }

    private ChlorineGasChemical(){
        super();
    }

    @Override
    public double getHeatCapacity() {
        return 30;
    }

    @Override
    public double getStandardEntropy() {
        return 0;
    }

    @Override
    public double getStandardEnthalpy() {
        return 0;
    }
}
