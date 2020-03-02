package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.FlightReservation;

import java.util.Date;

public class PostalNotification extends Notification {
    private Address address;

    public PostalNotification(Address address) {
        this.address = address;
    }

    public PostalNotification(FlightReservation flightReservation, int notificationId, Date createdOn, String content, Address address) {
        super(flightReservation, notificationId, createdOn, content);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
