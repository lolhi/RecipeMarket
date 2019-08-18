package com.rmarket.recipemarket;

public class Basket_Item {
    private String productImage;
    private String shopName;
    private String productName;
    private int deliverCost;
    private int productCost;
    private int productCount;

    Basket_Item(String shopName, String productName, int deliverCost, int productCost, int productCount, String productImage) {
        this.productImage = productImage;
        this.shopName = shopName;
        this.productName = productName;
        this.deliverCost = deliverCost;
        this.productCost = productCost;
        this.productCount = productCount;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }

    public int getDeliverCost() {
        return deliverCost;
    }

    public int getProductCost() {
        return productCost;
    }

    public int getProductCount() {
        return productCount;
    }
}