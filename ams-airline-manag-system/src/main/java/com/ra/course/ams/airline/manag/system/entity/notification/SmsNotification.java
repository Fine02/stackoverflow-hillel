package com.ra.course.ams.airline.manag.system.entity.notification;

public class SmsNotification extends Notification {
    private String email;

    public SmsNotification(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SmsNotification{" +
                "email='" + email + '\'' +
                ", notification=" + super.toString() +
                '}';
    }
}
