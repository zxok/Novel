package com.hzx.entity;

public class StoreHot {

    private String bookName;
    private String author;
    private String briefing;
    private String url;
    private String img;

    public StoreHot() {
    }

    @Override
    public String toString() {
        return "StoreHot{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", briefing='" + briefing + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBriefing() {
        return briefing;
    }

    public void setBriefing(String briefing) {
        this.briefing = briefing;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
