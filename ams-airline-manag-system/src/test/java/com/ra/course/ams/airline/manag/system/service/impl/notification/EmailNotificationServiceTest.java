package com.ra.course.ams.airline.manag.system.service.impl.notification;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.ReservationStatus;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.exception.ReservationWasNotModifiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailNotificationServiceTest {

    FlightInstance flightInstanceNotCanceledStatus =
            new FlightInstance.Builder().setStatus(FlightStatus.ARRIVED).build();
    FlightInstance flightInstanceCanceledStatus =
            new FlightInstance.Builder().setStatus(FlightStatus.CANCELED).build();

    FlightReservation flightReservationNotModifiedStatus =
            new FlightReservation(flightInstanceNotCanceledStatus, ReservationStatus.CONFIRMED);
    FlightReservation flightReservationFlightStatusCancelrd =
            new FlightReservation(flightInstanceCanceledStatus, ReservationStatus.CONFIRMED);
    FlightReservation flightReservationReservationStatusCancelrd =
            new FlightReservation(flightInstanceNotCanceledStatus, ReservationStatus.CANCELED);
    FlightReservation flightReservationReservationStatusAbandoned =
            new FlightReservation(flightInstanceNotCanceledStatus, ReservationStatus.ABANDONED);

    @Test
    public void whenNotificationWasNotModifiedThenThrowExeption() {

        Assertions.assertThrows(ReservationWasNotModifiedException.class, () ->
                new EmailNotificationService().send(flightReservationNotModifiedStatus));
    }

    @Test
    public void whenFlightStatusIsCanceledThenReturnFlightReservationInfo() {
         String expected = "FlightReservation{reservationNumber='null', " +
                "flightInstance=FlightInstance{\"id\": \"null\", \"flight\":null," +
                " \"departureTime\": \"null\", \"gate\": \"null\", \"status\":CANCELED," +
                " \"seats\":null, \"aircraft\":null, \"crews\":null, \"pilots\":null}, seatMap=null, " +
                "status=CONFIRMED, notifications=null, payment=null, passengers=null}send by email";
         String actual = new EmailNotificationService().send(flightReservationFlightStatusCancelrd);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void whenReservationStatusIsCanceledThenReturnFlightReservationInfo() {
        String expected = "FlightReservation{reservationNumber='null', " +
                "flightInstance=FlightInstance{\"id\": \"null\", \"flight\":null," +
                " \"departureTime\": \"null\", \"gate\": \"null\", \"status\":ARRIVED," +
                " \"seats\":null, \"aircraft\":null, \"crews\":null, \"pilots\":null}, seatMap=null, " +
                "status=CANCELED, notifications=null, payment=null, passengers=null}send by email";

        String actual = new EmailNotificationService().send(flightReservationReservationStatusCancelrd);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void whenReservationStatusIsAbandonedThenReturnFlightReservationInfo() {
        String expected = "FlightReservation{reservationNumber='null', " +
                "flightInstance=FlightInstance{\"id\": \"null\", \"flight\":null," +
                " \"departureTime\": \"null\", \"gate\": \"null\", \"status\":ARRIVED," +
                " \"seats\":null, \"aircraft\":null, \"crews\":null, \"pilots\":null}, seatMap=null, " +
                "status=ABANDONED, notifications=null, payment=null, passengers=null}send by email";
        String actual = new EmailNotificationService().send(flightReservationReservationStatusAbandoned);

        Assertions.assertEquals(expected, actual);
    }
}
