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
    private boolean isVerified;

    public Food() {
    }

    public Food(int foodID, String foodName, String brandName, String barcode, double calories, double carbs, double protein, double fats, boolean isVerified) {
        this.foodID = foodID;
        this.brandName = brandName;
        this.foodName = foodName;
        this.barcode = barcode;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fats = fats;
        this.isVerified = isVerified;
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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return foodName;
    }

}
