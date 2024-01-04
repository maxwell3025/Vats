package com.maxwell3025.vats.content.chemicals;

import com.maxwell3025.vats.api.Chemical;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public class WaterLiquidChemical extends Chemical {
    private static WaterLiquidChemical instance = null;

    public static WaterLiquidChemical getInstance() {
        if (instance == null) {
            instance = new WaterLiquidChemical();
        }
        return instance;
    }

    private WaterLiquidChemical() {
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
    public Vector4fc getColor() {
        return new Vector4f(0, 0.5f, 1, 0.5f);
    }

    @Override
    public String toFormulaString() {
        return "H2O";
    }
}
