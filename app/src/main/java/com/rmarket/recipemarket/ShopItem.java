package com.rmarket.recipemarket;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String productImage;
    private String shopName;
    private String productName;
    private int deliverCost;
    private int productCost;
    private int productCount;
    private String productDetail;

   ShopItem(String shopName, String productName, int deliverCost, int productCost, int productCount, String productImage, String productDetail)
    {
        this.productImage = productImage;
        this.shopName = shopName;
        this.productName = productName;
        this.deliverCost = deliverCost;
        this.productCost = productCost;
        this.productCount = productCount;
        this.productDetail = productDetail;
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

    public String getProductDetail() {
        return productDetail;
    }
}
