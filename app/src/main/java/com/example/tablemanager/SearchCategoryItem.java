package com.example.tablemanager;

import java.io.Serializable;

public class SearchCategoryItem implements Serializable {
    private String categoryName;
    private String realName;
    private int imgIcon;

    public SearchCategoryItem(String categoryName, int imgIcon) {
        this.categoryName = categoryName;
        this.imgIcon = imgIcon;
        this.realName = "";
    }

    public SearchCategoryItem(String categoryName, String realName, int imgIcon) {
        this.categoryName = categoryName;
        this.realName = realName;
        this.imgIcon = imgIcon;
    }

    public String getRealName() {
        return realName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getImgIcon() {
        return imgIcon;
    }
}
