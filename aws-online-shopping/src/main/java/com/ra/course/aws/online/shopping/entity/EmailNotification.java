package com.ra.course.aws.online.shopping.entity;

import java.util.Date;

public class EmailNotification extends  Notification {
    private String email;

    public EmailNotification(int notificationId, Date createdOn, String content) {
        super(notificationId, createdOn, content);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
