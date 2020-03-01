package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDate;

public class SMSNotification extends Notification {
    private String phone;

    public SMSNotification(int notificationId, LocalDate createdOn, String content) {
        super(notificationId, createdOn, content);
    }

    public SMSNotification(int notificationId, LocalDate createdOn, String content, String phone) {
        super(notificationId, createdOn, content);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
