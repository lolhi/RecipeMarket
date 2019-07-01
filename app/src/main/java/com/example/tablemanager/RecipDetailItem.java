package com.example.tablemanager;

public class RecipDetailItem {

    private String title, subtitle, caloris, hash1, hash2; // 제목, 날짜, 소제목
    int image, level;

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getcaloris() {
        return caloris;
    }

    public int getLevel() {
        return level;
    }

    public String getHash1() {
        return hash1;
    }

    public String getHash2() {
        return hash2;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public RecipDetailItem(int image, String title, int level, String subtitle, String caloris, String hash1, String hash2) {
        this.caloris = caloris;
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.image = image;
        this.title = title;
        this.level = level;
        this.subtitle = subtitle;
    }
}
