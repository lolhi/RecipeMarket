package com.rmarket.recipemarket;

public class NoticeItem {
    private String title;
    private String img;
    private String writer;
    private String contents;

    public NoticeItem(String title, String img, String writer, String contents) {
        this.title = title;
        this.img = img;
        this.writer = writer;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
