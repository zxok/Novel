package com.hzx.entity;

/**
 * @program: Novel
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-09-13 00:42
 **/
public class Updates {
    private int id;
    private String wordsNumber;
    private String date;
    private String newest;
    private int size;

    public Updates(int id, String wordsNumber, String date, String newest, int size) {
        this.id = id;
        this.wordsNumber = wordsNumber;
        this.date = date;
        this.newest = newest;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Updates{" +
                "id=" + id +
                ", wordsNumber='" + wordsNumber + '\'' +
                ", date='" + date + '\'' +
                ", newest='" + newest + '\'' +
                ", size=" + size +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordsNumber() {
        return wordsNumber;
    }

    public void setWordsNumber(String wordsNumber) {
        this.wordsNumber = wordsNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewest() {
        return newest;
    }

    public void setNewest(String newest) {
        this.newest = newest;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
