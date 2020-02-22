package com.ra.course.aws.online.shopping.entity;

import java.util.Date;

public class SMSNotification extends Notification {
    private String phone;

    public SMSNotification(int notificationId, Date createdOn, String content) {
        super(notificationId, createdOn, content);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
