package com.example.push1_1;

import java.util.ArrayList;

public class PeedRegisterItems {
    String like;
    String nickName;
    String days;
    String comments;
    ArrayList<String> tags;
    ArrayList<String> pictures;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public PeedRegisterItems(String like,String nickName, String days, String comments, ArrayList<String> tags, ArrayList<String> pictures) {
        this.like = like;
        this.nickName = nickName;
        this.days = days;
        this.comments = comments;
        this.tags = tags;
        this.pictures = pictures;
    }
}
