package com.example.proiectlicenta;

public class ExerciseLog {
    private ExerciseClass exercise;
    private int time;

    public ExerciseLog(ExerciseClass exercise, int time) {
        this.exercise = exercise;
        this.time = time;
    }

    public ExerciseClass getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseClass exercise) {
        this.exercise = exercise;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
