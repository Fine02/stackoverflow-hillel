package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;

import java.util.List;
import java.util.Optional;

public interface AircraftManagementService {

    Optional<Aircraft> addAircraft(Aircraft aircraft);

    Optional<Aircraft> updateAircraft(Aircraft aircraft);

    Optional<List<Flight>> getFlights(Aircraft aircraft);
}
