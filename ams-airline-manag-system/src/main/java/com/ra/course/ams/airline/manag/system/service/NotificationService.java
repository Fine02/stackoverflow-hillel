package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;

public interface NotificationService<T extends FlightReservation> {

    String send(T t);
}
