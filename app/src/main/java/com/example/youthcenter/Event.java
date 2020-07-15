package com.example.youthcenter;

import java.io.Serializable;

public class Event implements Serializable {

    private int id;
    private String title, date, tStart, tEnd, age, place, desc;
    private int image, visitorAmount = 0;

    public Event(String title, String date, String tStart, String tEnd, String age, String place, String desc, int visitorAmount, int image) {


        this.title = title;
        this.date = date;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.age = age;
        this.place = place;
        this.desc = desc;
        this.visitorAmount = visitorAmount;
        this.image = image;

    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getVisitorAmount() {
        return visitorAmount;
    }

    public void setVisitorAmount(int visitorAmount) {
        this.visitorAmount = visitorAmount;
    }

    public String gettStart() {
        return tStart;
    }

    public void settStart(String tStart) {
        this.tStart = tStart;
    }

    public String gettEnd() {
        return tEnd;
    }

    public void settEnd(String tEnd) {
        this.tEnd = tEnd;
    }
}
