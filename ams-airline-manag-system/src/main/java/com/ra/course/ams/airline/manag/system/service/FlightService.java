package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.List;

public interface FlightService {

    List<FlightInstance> getFlightInstances(Flight flight);
    Flight add(Flight flight);
    Flight update(Flight flight);
    boolean cancel(Flight flight);

}
