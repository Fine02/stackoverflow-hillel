package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {
    private int notificationId;
    private LocalDateTime createdOn;
    private String content;

    public Notification() {
    }

    public Notification(LocalDateTime createdOn, String content) {
        this.createdOn = createdOn;
        this.content = content;
    }

    public Notification(int notificationId, LocalDateTime createdOn, String content) {
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return notificationId == that.notificationId &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, createdOn, content);
    }
}
