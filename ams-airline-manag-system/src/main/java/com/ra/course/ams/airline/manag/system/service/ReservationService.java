package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.FlightReservation;
import com.ra.course.ams.airline.manag.system.entity.Itinerary;
import com.ra.course.ams.airline.manag.system.entity.Passenger;
import com.ra.course.ams.airline.manag.system.entity.person.Customer;

import java.util.List;

public interface ReservationService {

    FlightReservation create(FlightReservation reservation);
    void cancel(FlightReservation reservation);
    List<Passenger> getPassengers(FlightReservation reservation);

}
