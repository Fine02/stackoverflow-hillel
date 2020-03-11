package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;

public class EmailNotification extends Notification {
    private String email;


    public EmailNotification(String email) {
        this.email = email;
    }

    public EmailNotification(LocalDateTime createdOn, String content, String email) {
        super(createdOn, content);
        this.email = email;
    }

    public EmailNotification(int notificationId, LocalDateTime createdOn, String content, String email) {
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
