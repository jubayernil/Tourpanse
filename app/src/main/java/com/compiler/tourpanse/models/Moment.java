package com.compiler.tourpanse.models;


public class Moment {

    private int id;
    private String momentLocation;
    private String momentRemark;
    private String picturePath;
    private String pictureCaption;
    private int userId;
    private int eventId;

    public Moment(int id, String momentLocation, String momentRemark, String picturePath, String pictureCaption, int userId, int eventId) {
        this.id = id;
        this.momentLocation = momentLocation;
        this.momentRemark = momentRemark;
        this.picturePath = picturePath;
        this.pictureCaption = pictureCaption;
        this.userId = userId;
        this.eventId = eventId;
    }

    public Moment(int eventId, String momentLocation, String momentRemark, String picturePath, String pictureCaption, int userId) {
        this.eventId = eventId;
        this.momentLocation = momentLocation;
        this.momentRemark = momentRemark;
        this.picturePath = picturePath;
        this.pictureCaption = pictureCaption;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMomentLocation() {
        return momentLocation;
    }

    public void setMomentLocation(String momentLocation) {
        this.momentLocation = momentLocation;
    }

    public String getMomentRemark() {
        return momentRemark;
    }

    public void setMomentRemark(String momentRemark) {
        this.momentRemark = momentRemark;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureCaption() {
        return pictureCaption;
    }

    public void setPictureCaption(String pictureCaption) {
        this.pictureCaption = pictureCaption;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
