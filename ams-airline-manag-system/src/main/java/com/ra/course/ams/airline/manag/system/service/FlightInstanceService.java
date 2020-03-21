package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.util.List;
import java.util.Optional;

public interface FlightInstanceService {

    Optional<FlightInstance> add(FlightInstance flightInstance);

    void updateStatus(FlightInstance flightInstance, FlightStatus status);

    boolean cancel(FlightInstance flightInstance);

    boolean assignPilot(FlightInstance flightInstance, Pilot pilot);

    boolean assignCrew(FlightInstance flightInstance, Crew crew);

    List<Pilot> getAssignedPilots(FlightInstance flightInstance);

    List<Crew> getAssignedCrew(FlightInstance flightInstance);
}
