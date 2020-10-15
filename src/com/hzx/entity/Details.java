package com.hzx.entity;

import java.util.Arrays;

public class Details {

    private String img;
    private String Name;
    private String author;
    private String type;
    private String state;
    private String sum;
    private String time;
    private String latest;
    private String brief;
    private String[] latests;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String[] getLatests() {
        return latests;
    }

    public void setLatests(String[] latests) {
        this.latests = latests;
    }

    @Override
    public String toString() {
        return "Details{" +
                "Name='" + Name + '\'' +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", sum='" + sum + '\'' +
                ", time='" + time + '\'' +
                ", latest='" + latest + '\'' +
                ", brief='" + brief + '\'' +
                ", latests=" + Arrays.toString(latests) +
                "' img = "+ img + '\'' +
                '}';
    }
}
