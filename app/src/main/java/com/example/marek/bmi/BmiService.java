package com.example.marek.bmi;

import android.os.Bundle;

/**
 * Created by Marek on 08.03.2018.
 */

public class BmiService {
    public enum UnitStandard {
        ENGLISH,
        EUROPEAN
    }

    private double weight;
    private double height;
    private UnitStandard unitStandard;

    public final static double DEFAULT_WEIGHT = 70;
    public final static double DEFAULT_HEIGHT = 175;
    public final static double EU_TO_UK_WEIGHT_MULTIPLIER = 2.2;
    public final static double EU_TO_UK_HEIGHT_MULTIPLIER = 0.39;
    public final static int ENGLISH_BMI_FACTOR = 703;

    public BmiService() {
        this(DEFAULT_WEIGHT, DEFAULT_HEIGHT, UnitStandard.EUROPEAN);
    }

    public BmiService(double weight, double height, UnitStandard unitStandard)
    {
        setWeight(weight);
        setHeight(height);
        setUnitStandard(unitStandard);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setUnitStandard(UnitStandard unitStandard) {
        this.unitStandard = unitStandard;
    }

    public UnitStandard getUnitStandard() {
        return this.unitStandard;
    }

    public double getBmi() {
        if (unitStandard == UnitStandard.ENGLISH) {
            return getWeight() / Math.pow(getHeight(), 2) * ENGLISH_BMI_FACTOR;
        }
        return getWeight() / Math.pow(getHeight() / 100, 2);
    }
}
