package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    List<FlightInstance> getFlightInstances(Flight flight);

    Optional<Flight> add(Flight flight);

    Optional<Flight> update(Flight flight);

    boolean cancel(Flight flight);

}
