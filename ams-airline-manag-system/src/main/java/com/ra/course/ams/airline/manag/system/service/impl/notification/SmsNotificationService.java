package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.ReservationStatus;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.notification.SmsNotification;
import com.ra.course.ams.airline.manag.system.exceptions.ReservationWasNotModifiedException;
import com.ra.course.ams.airline.manag.system.service.NotificationService;

public class SmsNotificationService implements NotificationService <SmsNotification> {

    @Override
    public String send(final SmsNotification smsNotification) {
        if (smsNotification.getFlightReservation().getStatus() == ReservationStatus.CANCELED ||
                smsNotification.getFlightReservation().getStatus() == ReservationStatus.ABANDONED ||
                smsNotification.getFlightReservation().getFlightInstance().getStatus() == FlightStatus.CANCELED) {
            throw new ReservationWasNotModifiedException("Neither FlightReservation nor FlightInstance was not changed");
        }
        return smsNotification.toString();
    }
}
