package com.ra.course.aws.online.shopping.entity.notification;

import java.time.LocalDateTime;
import java.util.Objects;

public class SMSNotification extends Notification {
    private String phone;

    public SMSNotification() {
    }

    public SMSNotification(String phone) {
        this.phone = phone;
    }

    public SMSNotification(LocalDateTime createdOn, String content, String phone) {
        super(createdOn, content);
        this.phone = phone;
    }

    public SMSNotification(int notificationId, LocalDateTime createdOn, String content, String phone) {
        super(notificationId, createdOn, content);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SMSNotification that = (SMSNotification) o;
        return Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone);
    }
}
