package com.ra.course.aws.online.shopping.entity;

import java.time.LocalDate;

public class EmailNotification extends  Notification {
    private String email;


    public EmailNotification(int notificationId, LocalDate createdOn, String content) {
        super(notificationId, createdOn, content);
    }

    public EmailNotification(int notificationId, LocalDate createdOn, String content, String email) {
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
