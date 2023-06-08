package com.example.proiectlicenta;

import java.io.Serializable;

public class ExerciseClass implements Serializable {
    private String name;
    private int caloriesPerHour;

    public ExerciseClass(){

    }

    public ExerciseClass(String name, int caloriesPerHour) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public void setCaloriesPerHour(int caloriesPerHour) {
        this.caloriesPerHour = caloriesPerHour;
    }
}
