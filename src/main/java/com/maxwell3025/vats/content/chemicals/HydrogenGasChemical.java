package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;
import org.joml.Vector4f;
import org.joml.Vector4fc;

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
    public Vector4fc getColor() {
        return new Vector4f(0.0f);
    }

    @Override
    public String toFormulaString() {
        return "H2";
    }

    private HydrogenGasChemical() {
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
