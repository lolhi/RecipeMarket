package com.rmarket.recipemarket;

public class Ranking_Item{

    private String material;
    private double reduce;

    public String getMaterial() {
        return material;
    }

    public double getReduce() {
        return reduce;
    }

    Ranking_Item(String material, double amount)
    {
        this.material = material;
        this.reduce = amount;
    }


}

