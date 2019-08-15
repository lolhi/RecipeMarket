package com.rmarket.recipemarket;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private int productImage;
    private String shopName;
    private String productName;
    private int deliverCost;
    private int productCost;
    private int productCount;
    private int productDetail;

   ShopItem(String shopName, String productName, int deliverCost, int productCost, int productCount, int productImage,int productDetail)
    {
        this.productImage = productImage;
        this.shopName = shopName;
        this.productName = productName;
        this.deliverCost = deliverCost;
        this.productCost = productCost;
        this.productCount = productCount;
        this.productDetail =productDetail;
    }

    public int getProductDetail() {
        return productDetail;
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
