package com.rmarket.recipemarket;

public class Material_Item {

    private String material;
    private String amount;

    public String getMaterial() {
        return material;
    }

    public String getAmount() {
        return amount;
    }

    Material_Item(String material, String amount)
    {
        this.material = material;
        this.amount = amount;
    }


}

