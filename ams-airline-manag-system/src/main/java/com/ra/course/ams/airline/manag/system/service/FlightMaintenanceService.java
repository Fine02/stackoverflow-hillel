package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.entity.person.*;

import java.sql.Time;
import java.util.Date;

public interface FlightMaintenanceService {

    int addFlight(Flight flight);

    int addWeeklySchedule(WeeklySchedule weeklySchedule);

    int addCustomSchedule(CustomSchedule customSchedule);

    void cancelFlight(String flightNumber);

    void cancelWeeklySchedule(String flightNumber, int dayOfWeek, Time departureTime);

    void cancelCustomSchedule(String flightNumber, Date customDate, Time departureTime);

    void assignAircraft(FlightInstance flightInstance, Aircraft aircraft);

    void removeAircraft(FlightInstance flightInstance);

    void assignPilot(FlightInstance flightInstance, Pilot pilot);

    void removePilot(FlightInstance flightInstance, String id);

    void assignCrew(FlightInstance flightInstance, Crew crew);

    void removeCrew(FlightInstance flightInstance, String id);

}
