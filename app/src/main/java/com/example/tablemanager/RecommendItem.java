package com.example.tablemanager;

public class RecommendItem {
    private String title, subtitle, image, time, level; // 제목, 날짜, 소제목

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTime() {
        return time;
    }

    public int getImage() {
        return image;
    }

    public String getLevel() {
        return level;
    }

    public RecommendItem(String title, String subtitle, String time, String image, String level) {
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.image = image;
        this.level = level;
    }

    public RecommendItem(String image, String title, String level, String subtitle){
        this.image = image;
        this.title = title;
        this.level = level;
        this.subtitle = subtitle;
    }
}
