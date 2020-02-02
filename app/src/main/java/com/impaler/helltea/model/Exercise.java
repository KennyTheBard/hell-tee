package com.impaler.helltea.model;

import java.util.HashMap;
import java.util.Map;

public class Exercise implements Comparable<Exercise> {

    private String id;

    private String date;
    private ExerciseType exerciseType;
    private Integer value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return exerciseType.getName() + " on " + date + " : " + value + " " + exerciseType.getMeasureUnit();
    }

    @Override
    public int compareTo(Exercise exercise) {
        String[] a = this.getDate().split("-");
        String[] b = exercise.getDate().split("-");
        for (int i = 2; i >= 0; i--) {
            int cmp = Integer.valueOf(a[i]) - Integer.valueOf(b[i]);
            if (cmp != 0) return -cmp; // descending order
        }
        return 0;
    }

}
