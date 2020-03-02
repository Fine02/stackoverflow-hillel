package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.notification.Notification;
import com.ra.course.ams.airline.manag.system.service.NotificationService;

public class EmailNotificationService implements NotificationService {

    @Override
    public boolean send(final Notification notification) {
        //log.info("Current message was created on: " + notification.getCreatedOn())
        return false;
    }
}
