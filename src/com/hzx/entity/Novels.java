package com.hzx.entity;

public class Novels
{
    private int id;
    private String bookName;
    private String author;
    private String wordsNumber;
    private String img;
    private String date;
    private String url;
    private String newest;
    private int size;
    private int readingRecord;

    public Novels(int id, String bookName, String author, String wordsNumber, String img, String date, String url,
                  String newest, int size, int readingRecord) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.wordsNumber = wordsNumber;
        this.img = img;
        this.date = date;
        this.url = url;
        this.newest = newest;
        this.size = size;
        this.readingRecord = readingRecord;
    }

    public Novels (int id, String bookName, String author, String wordsNumber,
                   String img, String date, String url, String newest, int size)
    {
        this.id=id;
        this.bookName = bookName;
        this.author = author;
        this.wordsNumber = wordsNumber;
        this.img=img;
        this.date = date;
        this.url=url;
        this.newest = newest;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }
    public String getAuthor() {
        return author;
    }
    public String getWordsNumber() {
        return wordsNumber;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
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

    public int getReadingRecord() {
        return readingRecord;
    }

    public void setReadingRecord(int readingRecord) {
        this.readingRecord = readingRecord;
    }

    @Override
    public String toString() {
        return "Novels{" +
                "\nid=" + id +
                ", \nbook='" + bookName + '\'' +
                ", \nauthor='" + author + '\'' +
                ", \nwordsNumber='" + wordsNumber + '\'' +
                ", \nimg='" + img + '\'' +
                ", \ndate='" + date + '\'' +
                ", \nurl='" + url + '\'' +
                ", \nnewest='" + newest + '\'' +
                '}';
    }
}

