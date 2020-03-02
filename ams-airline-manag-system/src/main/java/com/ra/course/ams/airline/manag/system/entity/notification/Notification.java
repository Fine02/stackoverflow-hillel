package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.Date;

public class Notification {
    private FlightReservation flightReservation;
    private FlightInstance flightInstance;
    private int notificationId;
    private Date createdOn;
    private String content;

    public Notification() {
    }

    public Notification(FlightReservation flightReservation, FlightInstance flightInstance, int notificationId, Date createdOn, String content) {
        this.flightReservation = flightReservation;
        this.flightInstance = flightInstance;
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
    }

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

    public FlightReservation getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(FlightReservation flightReservation) {
        this.flightReservation = flightReservation;
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

    @Override
    public String toString() {
        return "Notification{" +
                "flightReservation=" + flightReservation +
                ", flightInstance=" + flightInstance +
                ", notificationId=" + notificationId +
                ", createdOn=" + createdOn +
                ", content='" + content + '\'' +
                '}';
    }
}
