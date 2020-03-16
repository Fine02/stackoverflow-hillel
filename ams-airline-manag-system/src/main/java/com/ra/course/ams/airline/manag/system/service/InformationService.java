package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface InformationService {

    WeeklySchedule checkFlightWeeklySchedule(String flightNumber);

    CustomSchedule checkFlightCustomSchedule(String flightNumber);

    Time checkDepartureTime(FlightInstance flightInstance);

    List<FlightSeat> checkAvailableSeats(FlightInstance flightInstance);

    Time checkArrivalTime(FlightInstance flightInstance);

    List<Flight> searchFlightByDate(Date date);

    List<Flight> searchFlightByDepartureAirport(Airport airport);

    List<Flight> searchFlightByArrivalAirport(Airport airport);

}
