package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.notification.Notification;

public interface NotificationService<T extends Notification> {

    String send(T t);
}
