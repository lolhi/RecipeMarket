package com.rmarket.recipemarket;

public class Commetn_Item {

    private String Text;
    private String profile ;
    private String time;
    private String name;

    Commetn_Item(String profile,String name,String Text,String time)
    {
        this.profile = profile;
        this.name = name;
        this.Text = Text;
        this.time = time;

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
}
