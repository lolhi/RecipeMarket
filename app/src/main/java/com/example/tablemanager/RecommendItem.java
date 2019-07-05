package com.example.tablemanager;
public class RecommendItem {
    private String title, subtitle,time; // 제목, 날짜, 소제목
    int image,level;

    public String getTime() { return time; }
    public int getImage() { return image;}
    public String getTitle() { return title; }
   public int getLevel(){return level;}
    public String getSubtitle() { return subtitle; }

    public RecommendItem(int image, String title, int level, String subtitle,String time){

        this.image = image;
        this.title = title;
        this.level = level;
        this.subtitle = subtitle;
    }
    public RecommendItem(int image, String title, int level, String subtitle){

        this.image = image;
        this.title = title;
        this.level = level;
        this.subtitle = subtitle;
    }


}
