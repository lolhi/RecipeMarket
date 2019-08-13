package com.rmarket.recipemarket;

public class Basket_Item {
    private int productImage;
    private String shopName;
    private String productName;
    private String deliverCost;
    private String productCost;
    private String productCount;

    Basket_Item(String shopName, String productName, String deliverCost, String productCost, String productCount, int productImage)
    {
        this.productImage =productImage;
        this.shopName = shopName;
        this.productName = productName;
        this.deliverCost = deliverCost;
        this.productCost = productCost;
        this.productCount = productCount;
    }



    public int getProductImage() {
        return productImage;
    }


    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }

    public String getDeliverCost() {
        return deliverCost;
    }

    public String getProductCost() {
        return productCost;
    }

    public String getProductCount() {
        return productCount;
    }
}
