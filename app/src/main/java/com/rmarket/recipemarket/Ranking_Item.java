package com.rmarket.recipemarket;

public class Ranking_Item{

    private String material;
    private String reduce;

    public String getMaterial() {
        return material;
    }

    public String getReduce() {
        return reduce;
    }

    Ranking_Item(String material, String amount)
    {
        this.material = material;
        this.reduce = amount;
    }


}

