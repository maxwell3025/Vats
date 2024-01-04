package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;
import org.joml.Vector4f;
import org.joml.Vector4fc;

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

    @Override
    public Vector4fc getColor() {
        return new Vector4f(0.0f);
    }

    @Override
    public String toFormulaString() {
        return "HCl";
    }
    private HydrogenChlorideGasChemical(){
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
        return -92300;
    }
}
