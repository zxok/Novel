package com.hzx.entity;

/**
 * @program: Novel
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-09-15 19:49
 **/
public class MorePageStore {
    private String head;
    private String tail;
    private String prev;
    private String next;
    private String my;

    public MorePageStore() {
    }

    public MorePageStore(String head, String tail, String prev, String next) {
        this.head = head;
        this.tail = tail;
        this.prev = prev;
        this.next = next;
    }

    public MorePageStore(String head, String tail, String prev, String next, String my) {
        this.head = head;
        this.tail = tail;
        this.prev = prev;
        this.next = next;
        this.my = my;
    }

    @Override
    public String toString() {
        return "MorePageStore{" +
                " \n head='" + head + '\'' +
                ", \n tail='" + tail + '\'' +
                ", \n prev='" + prev + '\'' +
                ", \n next='" + next + '\'' +
                ", \n my='" + my + '\'' +
                '}';
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getMy() {
        return my;
    }

    public void setMy(String my) {
        this.my = my;
    }
}
