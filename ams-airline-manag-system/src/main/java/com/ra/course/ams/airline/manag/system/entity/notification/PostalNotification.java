package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.Address;

public class PostalNotification extends Notification {
    private Address address;

    public PostalNotification(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PostalNotification{" +
                "address=" + address +
                ", notification=" + super.toString() +
                '}';
    }
}
