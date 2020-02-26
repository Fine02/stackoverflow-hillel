package com.ra.course.ams.airline.manag.system.service.draft;

import com.ra.course.ams.airline.manag.system.entity.notification.Notification;

public interface NotificationService<T extends Notification> {

    boolean send(T t);

}
