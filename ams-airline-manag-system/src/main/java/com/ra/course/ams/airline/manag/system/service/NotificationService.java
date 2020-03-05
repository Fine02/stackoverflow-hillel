package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.notification.Notification;

public interface NotificationService<T extends FlightReservation> {

    String send(T t);
}
