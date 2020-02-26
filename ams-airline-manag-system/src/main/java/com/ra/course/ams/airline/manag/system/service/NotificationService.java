package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.notification.Notification;

public interface NotificationService<T extends Notification> {

    boolean send(T t);

}
