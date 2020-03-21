package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InformationService {

    Optional<WeeklySchedule> checkFlightWeeklySchedule(String flightNumber);

    Optional<CustomSchedule> checkFlightCustomSchedule(String flightNumber);

    Optional<Time> checkDepartureTime(FlightInstance flightInstance);

    Optional<List<FlightSeat>> checkAvailableSeats(FlightInstance flightInstance);

    Optional<Time> checkArrivalTime(FlightInstance flightInstance);

    Optional<List<Flight>> searchFlightByDate(Date date);

    Optional<List<Flight>> searchFlightByDepartureAirport(Airport airport);

    Optional<List<Flight>> searchFlightByArrivalAirport(Airport airport);

}
