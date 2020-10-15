package com.hzx.entity;

public class Types {

    private String type;
    private String Name;
    private String latest;
    private String author;
    private String time;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Types{" +
                "type='" + type + '\'' +
                ", Name='" + Name + '\'' +
                ", latest='" + latest + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
