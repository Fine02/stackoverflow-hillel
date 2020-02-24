package com.ra.course.ams.airline.manag.system.entity.notification;

import java.util.Date;

public class SmsNotification extends Notification {
    private String email;

    public SmsNotification(int notificationId, Date createdOn, String content, String email) {
        super(notificationId, createdOn, content);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
