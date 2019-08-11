package com.rmarket.recipemarket;

public class Commetn_Item {

    private String Text;
    private String profile ;
    private String time;
    private String name;
    private String sRecipeId;

    Commetn_Item(String profile,String name,String Text,String time, String sRecipeId)
    {
        this.profile = profile;
        this.name = name;
        this.Text = Text;
        this.time = time;
        this.sRecipeId = sRecipeId;
    }

    public String getCommentText() {
        return Text;
    }

    public String getCommentProfile() {
        return profile;
    }

    public String getCommetTime() {
        return time;
    }

    public String getCommentName() {
        return name;
    }

    public String getsRecipeId() {
        return sRecipeId;
    }
}
