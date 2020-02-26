package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface InformationService {

    WeeklySchedule checkFlightWeeklySchedule(String flightNumber);

    CustomSchedule checkFlightCustomSchedule(String flightNumber);

    Time checkDepartureTime(FlightInstance flightInstance);

    List<FlightSeat> checkAvaibleSeats(FlightInstance flightInstance);

    Time checkArrivalTime(FlightInstance flightInstance);

    Flight searchFlightByDate(Date date);

    Flight searchFlightBySourceAirport(String airportCode);

    Flight searchFlightByDestinationAirport(String airportCode);

    List<Pilot> getPilots(String flightNumber);

    List<Crew> getCrew(String flightNumber);

    Account getAccountById(String id);

    Person getPersonById(String id);

    Person getPersonByEmail(String email);

    Person getPersonByPhone(String phone);
}
