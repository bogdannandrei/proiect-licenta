package com.example.proiectlicenta;

public class ExerciseLog {
    private String exerciseName;
    private String time;
    private int day;
    private int month;
    private int year;
    private double caloriesBurned;

    public ExerciseLog(){

    }

    public ExerciseLog(String exercise, String time, int day, int month, int year, int caloriesBurned) {
        this.exerciseName = exerciseName;
        this.time = time;
        this.day = day;
        this.month = month;
        this.year = year;
        this.caloriesBurned = caloriesBurned;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}
