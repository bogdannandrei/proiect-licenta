package com.example.proiectlicenta;

import java.util.Date;

public class FoodLog {
    private String foodName;
    private String mealType;
    private double calories;
    private double fats;
    private double protein;
    private double carbs;
    private int year;
    private int month;
    private int day;

    private String servingSize;
    private String brandName;
    public FoodLog(){

    }
    public String getFoodName() {
        return foodName;
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

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public String getBrandName() {
        return brandName;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "FoodLog{" +
                "foodName='" + foodName + '\'' +
                ", mealType='" + mealType + '\'' +
                ", calories=" + calories +
                ", fats=" + fats +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", servingSize='" + servingSize + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
