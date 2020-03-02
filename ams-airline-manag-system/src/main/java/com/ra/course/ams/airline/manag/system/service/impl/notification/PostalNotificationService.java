package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.ReservationStatus;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.notification.PostalNotification;
import com.ra.course.ams.airline.manag.system.exceptions.ReservationWasNotModifiedException;
import com.ra.course.ams.airline.manag.system.service.NotificationService;

public class PostalNotificationService implements NotificationService <PostalNotification> {

    @Override
    public String send(final PostalNotification postalNotification) {
        if (postalNotification.getFlightReservation().getStatus() == ReservationStatus.CANCELED ||
                postalNotification.getFlightReservation().getStatus() == ReservationStatus.ABANDONED ||
                postalNotification.getFlightReservation().getFlightInstance().getStatus() == FlightStatus.CANCELED) {
            throw new ReservationWasNotModifiedException("Neither FlightReservation nor FlightInstance was not changed");
        }
        return postalNotification.toString();
    }
}
