package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;

import java.util.Date;

public class EmailNotification extends Notification {
    private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    public EmailNotification(FlightReservation flightReservation, int notificationId, Date createdOn, String content, String email) {
        super(flightReservation, notificationId, createdOn, content);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
