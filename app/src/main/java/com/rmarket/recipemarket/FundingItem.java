package com.rmarket.recipemarket;

import java.io.Serializable;

public class FundingItem  implements Serializable {
    private String sFundingName;
    private String sProductName;
    private String sProductSubName;
    private String sGoalAmount;
    private String sPresentAmount;
    private int iFundingPercent;
    private int iFundingMark;
    private int iProductImg;

    public FundingItem(String sFundingName, String sProductName, String sProductSubName, String sGoalAmount, String sPresentAmount, int iFundingPercent, int iFundingMark, int iProductImg) {
        this.sFundingName = sFundingName;
        this.sProductName = sProductName;
        this.sProductSubName = sProductSubName;
        this.sGoalAmount = sGoalAmount;
        this.sPresentAmount = sPresentAmount;
        this.iFundingPercent = iFundingPercent;
        this.iFundingMark = iFundingMark;
        this.iProductImg = iProductImg;
    }

    public int getiProductImg() {
        return iProductImg;
    }

    public String getsFundingName() {
        return sFundingName;
    }

    public String getsProductName() {
        return sProductName;
    }

    public String getsProductSubName() {
        return sProductSubName;
    }

    public String getsGoalAmount() {
        return sGoalAmount;
    }

    public String getsPresentAmount() {
        return sPresentAmount;
    }

    public int getiFundingPercent() {
        return iFundingPercent;
    }

    public int getiFundingMark() {
        return iFundingMark;
    }
}
