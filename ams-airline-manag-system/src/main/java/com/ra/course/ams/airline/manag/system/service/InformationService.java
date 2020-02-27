package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface InformationService {

    WeeklySchedule checkFlightWeeklySchedule(String flightNumber);

    CustomSchedule checkFlightCustomSchedule(String flightNumber);

    Time checkDepartureTime(FlightInstance flightInstance);

    List<FlightSeat> checkAvailableSeats(FlightInstance flightInstance);

    Time checkArrivalTime(FlightInstance flightInstance);

    Flight searchFlightByDate(Date date);

    Flight searchFlightBySourceAirport(String airportCode);

    Flight searchFlightByDestinationAirport(String airportCode);

}
