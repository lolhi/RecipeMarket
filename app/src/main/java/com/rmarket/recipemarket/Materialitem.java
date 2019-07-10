package com.rmarket.recipemarket;

public class Materialitem {
    private String meterialNum;
    private String meterialName;
    private String meterialCpcty;
    private String meterialTypeName;

    public Materialitem(String meterialNum, String meterialName, String meterialCpcty, String meterialTypeName) {
        this.meterialNum = meterialNum;
        this.meterialName = meterialName;
        this.meterialCpcty = meterialCpcty;
        this.meterialTypeName = meterialTypeName;
    }

    public String getMeterialNum() {
        return meterialNum;
    }

    public String getMeterialName() {
        return meterialName;
    }

    public String getMeterialCpcty() {
        return meterialCpcty;
    }

    public String getMeterialTypeName() {
        return meterialTypeName;
    }
}
