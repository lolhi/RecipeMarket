package com.rmarket.recipemarket;

import java.io.Serializable;

public class PaymentItem implements Serializable {
    private String sProductName;
    private int iQuantity;
    private int iTotalAmount;

    public PaymentItem(String sProductName, int iQuantity, int iTotalAmount) {
        this.sProductName = sProductName;
        this.iQuantity = iQuantity;
        this.iTotalAmount = iTotalAmount;
    }

    public String getsProductName() {
        return sProductName;
    }

    public int getiQuantity() {
        return iQuantity;
    }

    public int getiTotalAmount() {
        return iTotalAmount;
    }
}
