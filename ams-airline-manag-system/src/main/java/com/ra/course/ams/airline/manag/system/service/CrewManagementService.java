package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;

import java.util.Optional;

public interface CrewManagementService {

    Optional<Crew> addFlightInstance(Crew crew, FlightInstance flightInstance);

    Optional<Crew> removeFlightInstance(Crew crew, FlightInstance flightInstance);
}
