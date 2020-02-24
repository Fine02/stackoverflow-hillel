package com.ra.course.ams.airline.manag.system.entity;

import java.util.Date;

public class Notification {
    private int notificationId;
    private Date createdOn;
    private String content;

    public Notification() {
    }

    public Notification(int notificationId, Date createdOn, String content) {
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
