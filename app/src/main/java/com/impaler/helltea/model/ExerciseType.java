package com.impaler.helltea.model;

import androidx.annotation.NonNull;

public class ExerciseType {

    private String name;
    private String measureUnit;

    public ExerciseType() {
    }

    public ExerciseType(String name, String measureUnit) {
        this.name = name;
        this.measureUnit = measureUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
