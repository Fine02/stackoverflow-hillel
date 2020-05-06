package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;
import java.util.Objects;

public class EmailNotification extends Notification {
    private String email;

    public EmailNotification() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailNotification that = (EmailNotification) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
