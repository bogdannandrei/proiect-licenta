package com.example.proiectlicenta;

public class Food {
    private String brandName;
    private String foodName;
    private String barcode;
    private int calories;
    private int carbs;
    private int protein;
    private int fats;

    public Food() {
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

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProtein() {
        return protein;
    }

    public int getFats() {
        return fats;
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

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
}
