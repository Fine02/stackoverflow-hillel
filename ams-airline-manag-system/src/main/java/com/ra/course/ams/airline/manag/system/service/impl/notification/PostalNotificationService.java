package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.ReservationStatus;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.exception.ReservationWasNotModifiedException;
import com.ra.course.ams.airline.manag.system.service.NotificationService;

public class PostalNotificationService implements NotificationService <FlightReservation> {

    @Override
    public String send(final FlightReservation flightReservation) {
        if (flightReservation.getStatus() == ReservationStatus.CANCELED ||
                flightReservation.getStatus() == ReservationStatus.ABANDONED ||
                flightReservation.getFlightInstance().getStatus() == FlightStatus.CANCELED) {
            return flightReservation.toString() + "send by post office";
        }
        throw new ReservationWasNotModifiedException("Neither FlightReservation nor FlightInstance was not changed");
    }
}