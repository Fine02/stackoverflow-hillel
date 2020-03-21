package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.Passenger;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<FlightReservation> create(FlightReservation reservation);

    void cancel(FlightReservation reservation);

    Optional<List<Passenger>> getPassengers(FlightReservation reservation);
}
