package com.compiler.tourpanse.models;


public class Event {

    private int eventId;
    private String eventLocation;
    private String travelStartingDate;
    private String travelDuration;
    private String estimatedBudget;
    private int userId;

    public Event(int eventId, String eventLocation, String travelStartingDate, String travelDuration, String estimatedBudget, int userId) {
        this.eventId = eventId;
        this.eventLocation = eventLocation;
        this.travelStartingDate = travelStartingDate;
        this.travelDuration = travelDuration;
        this.estimatedBudget = estimatedBudget;
        this.userId = userId;
    }

    public Event(int userId, String eventLocation, String travelStartingDate, String travelDuration, String estimatedBudget) {
        this.userId = userId;
        this.eventLocation = eventLocation;
        this.travelStartingDate = travelStartingDate;
        this.travelDuration = travelDuration;
        this.estimatedBudget = estimatedBudget;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getTravelStartingDate() {
        return travelStartingDate;
    }

    public void setTravelStartingDate(String travelStartingDate) {
        this.travelStartingDate = travelStartingDate;
    }

    public String getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(String travelDuration) {
        this.travelDuration = travelDuration;
    }

    public String getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(String estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
