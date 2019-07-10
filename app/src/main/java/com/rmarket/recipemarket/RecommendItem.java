package com.rmarket.recipemarket;

import java.io.Serializable;

public class RecommendItem implements Serializable {
    private String title, subtitle, image, time, level, cal, id; // 제목, 날짜, 소제목

    public RecommendItem(String title, String subtitle, String time, String image, String level) {
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.image = image;
        this.level = level;
    }

    public RecommendItem(String image, String title, String level, String subtitle) {
        this.image = image;
        this.title = title;
        this.level = level;
        this.subtitle = subtitle;
    }

    public RecommendItem(String title, String subtitle, String time, String image, String level, String cal, String id) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.time = time;
        this.level = level;
        this.cal = cal;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getCal() {
        return cal;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }
}
