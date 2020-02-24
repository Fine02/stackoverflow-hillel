package com.ra.course.ams.airline.manag.system.entity.notification;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.notification.Notification;

import java.util.Date;

public class PostalNotification extends Notification {
    private Address address;

    public PostalNotification(int notificationId, Date createdOn, String content, Address address) {
        super(notificationId, createdOn, content);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
