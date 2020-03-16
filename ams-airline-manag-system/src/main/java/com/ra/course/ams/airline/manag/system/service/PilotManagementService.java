package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

public interface PilotManagementService {

    Pilot addFlightInstance(Pilot pilot, FlightInstance flightInstance);
    Pilot removeFlightInstance(Pilot pilot, FlightInstance flightInstance);

}
