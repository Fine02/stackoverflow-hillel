package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;

import java.util.Date;

public class SmsNotification extends Notification {
    private String email;

    public SmsNotification(FlightReservation flightReservation, int notificationId, Date createdOn, String content, String email) {
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
