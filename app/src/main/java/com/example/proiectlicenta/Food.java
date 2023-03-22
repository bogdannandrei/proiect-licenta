package com.example.proiectlicenta;

import java.io.Serializable;

public class Food implements Serializable {

    private int foodID;
    private String brandName;
    private String foodName;
    private String barcode;
    private double calories;
    private double carbs;
    private double protein;
    private double fats;

    public Food() {
    }

    public int getFoodID() {
        return foodID;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getCalories() {
        return calories;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getProtein() {
        return protein;
    }

    public double getFats() {
        return fats;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }
}
