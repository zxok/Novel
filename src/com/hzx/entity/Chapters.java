package com.hzx.entity;

public class Chapters {
    private int id;
    private String names;
    private String content;

    public Chapters(int id, String names, String content) {
        this.id=id;
        this.names = names;
        this.content = content;
    }

    public Chapters() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }
    public void setNames(String zjm) {
        this.names = zjm;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String nr) {
        this.content = nr;
    }

    @Override
    public String toString() {
        return this.getId()+"\n"+this.getNames()+"\n"+this.getContent();
    }
}

