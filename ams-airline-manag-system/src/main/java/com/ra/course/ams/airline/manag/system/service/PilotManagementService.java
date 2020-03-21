package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.util.Optional;

public interface PilotManagementService {

    Optional<Pilot> addFlightInstance(Pilot pilot, FlightInstance flightInstance);

    Optional<Pilot> removeFlightInstance(Pilot pilot, FlightInstance flightInstance);
}
