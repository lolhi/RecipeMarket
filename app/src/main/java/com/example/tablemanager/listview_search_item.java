package com.example.tablemanager;

import android.graphics.drawable.Drawable;

public class  listview_search_item {
    private Drawable iconDrawable ;
    private String titleStr ;

    public  listview_search_item(Drawable iconDrawable, String titleStr)
    {
        this.iconDrawable = iconDrawable;
        this.titleStr = titleStr;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }


    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }

}