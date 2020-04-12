package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;

public class SMSNotification extends Notification {
    private String phone;

    public SMSNotification() {
    }

    public SMSNotification(String phone) {
        this.phone = phone;
    }

    public SMSNotification(LocalDateTime createdOn, String content, String phone) {
        super(createdOn, content);
        this.phone = phone;
    }

    public SMSNotification(int notificationId, LocalDateTime createdOn, String content, String phone) {
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
