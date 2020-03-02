package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;

import java.util.Date;

public class Notification {
    private FlightReservation flightReservation;
    private int notificationId;
    private Date createdOn;
    private String content;

    public Notification() {
    }

    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(FlightReservation flightReservation) {
        this.flightReservation = flightReservation;
    }

    public Notification(FlightReservation flightReservation, int notificationId, Date createdOn, String content) {
        this.flightReservation = flightReservation;
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
