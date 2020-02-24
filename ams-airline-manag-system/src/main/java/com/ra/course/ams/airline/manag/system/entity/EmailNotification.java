package com.ra.course.ams.airline.manag.system.entity;

public class EmailNotification extends Notification {
    private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
