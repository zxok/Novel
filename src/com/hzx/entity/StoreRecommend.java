package com.hzx.entity;

import java.util.Arrays;

public class StoreRecommend {

    private String h2;
    private String bookName;
    private String briefing;
    private String img;
    private String url;
    private String[] bookNames;
    private String[] urls;

    @Override
    public String toString() {
        return "StoreRecommend{" +
                "h2='" + h2 + '\'' +
                ", bookName='" + bookName + '\'' +
                ", briefing='" + briefing + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", bookNames=" + Arrays.toString(bookNames) +
                ", urls=" + Arrays.toString(urls) +
                '}';
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getBookNames() {
        return bookNames;
    }

    public void setBookNames(String[] bookNames) {
        this.bookNames = bookNames;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
