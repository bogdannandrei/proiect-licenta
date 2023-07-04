package com.example.proiectlicenta;

import java.io.Serializable;
import java.util.Date;

public class FoodLog implements Serializable {

    private Food food;
    private int foodLogID;
    private String mealType;
    private double calories;
    private double numberOfServings;
    private double fats;
    private double protein;
    private double carbs;
    private int year;
    private int month;
    private int day;
    private String servingSize;
    public FoodLog(){
    }

    public double getCalories() {
        return calories;
    }

    public double getFats() {
        return fats;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getMealType() {
        return mealType;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public int getFoodLogID() {
        return foodLogID;
    }

    public void setFoodLogID(int foodLogID) {
        this.foodLogID = foodLogID;
    }

    public double getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(double numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "FoodLog{" +
                ", mealType='" + mealType + '\'' +
                ", calories=" + calories +
                ", fats=" + fats +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", servingSize='" + servingSize + '\'' +
                '}';
    }
}
