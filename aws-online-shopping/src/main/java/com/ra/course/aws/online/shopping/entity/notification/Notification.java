package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;

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
}
