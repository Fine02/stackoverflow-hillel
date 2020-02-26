package com.ra.course.ams.airline.manag.system.service.draft;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;

import java.util.List;

public interface AircraftManagmentService {

    Aircraft addAircraft(Aircraft aircraft);
    Aircraft updateAircraft(Aircraft aircraft);
    List<Flight> getFlights(Aircraft aircraft);

}
