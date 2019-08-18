package com.rmarket.recipemarket;

import java.io.Serializable;

public class PaymentItem implements Serializable {
    private String sProductName;
    private int iProductPrice;
    private int iDeliveryCost;
    private int iQuantity;

    public PaymentItem(String sProductName, int iProductPrice, int iDeliveryCost, int iQuantity) {
        this.sProductName = sProductName;
        this.iProductPrice = iProductPrice;
        this.iDeliveryCost = iDeliveryCost;
        this.iQuantity = iQuantity;
    }

    public String getsProductName() {
        return sProductName;
    }

    public int getiProductPrice() {
        return iProductPrice;
    }

    public int getiDeliveryCost() {
        return iDeliveryCost;
    }

    public int getiQuantity() {
        return iQuantity;
    }
}
