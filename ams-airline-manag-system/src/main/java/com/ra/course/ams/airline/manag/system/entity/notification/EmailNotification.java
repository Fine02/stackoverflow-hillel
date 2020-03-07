package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.Date;

public class EmailNotification extends Notification {
    private String email;
    transient private Notification notification;

    public EmailNotification(String email, Notification notification) {
        this.email = email;
        this.notification = notification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailNotification{" +
                "email='" + email + '\'' +
                ", notification=" + super.toString() +
                '}';
    }
}
