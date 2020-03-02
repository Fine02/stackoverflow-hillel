package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.ReservationStatus;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.notification.EmailNotification;
import com.ra.course.ams.airline.manag.system.exceptions.ReservationWasNotModifiedException;
import com.ra.course.ams.airline.manag.system.service.NotificationService;

public class EmailNotificationService implements NotificationService <EmailNotification> {

    @Override
    public String send(final EmailNotification emailNotification) {
        if (emailNotification.getFlightReservation().getStatus() == ReservationStatus.CANCELED ||
                emailNotification.getFlightReservation().getStatus() == ReservationStatus.ABANDONED ||
                emailNotification.getFlightReservation().getFlightInstance().getStatus() == FlightStatus.CANCELED) {
            throw new ReservationWasNotModifiedException("Neither FlightReservation nor FlightInstance was not changed");
        }
       return emailNotification.toString();

    }
}
