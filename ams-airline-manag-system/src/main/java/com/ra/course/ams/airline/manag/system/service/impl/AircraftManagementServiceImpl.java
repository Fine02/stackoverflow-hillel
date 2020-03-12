package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.AircraftManagementService;

import java.util.List;

public class AircraftManagementServiceImpl implements AircraftManagementService {

    transient private final Repository<Aircraft, String> aircraftRepo;

    public AircraftManagementServiceImpl(final Repository<Aircraft, String> aircraftRepo) {
        this.aircraftRepo = aircraftRepo;
    }

    @Override
    public Aircraft addAircraft(final Aircraft aircraft) {
        if (aircraft == null) {
            throw new IllegalArgumentException("Cannot process add aircraft operation for null value argument.");
        }
        aircraftRepo.addInstance(aircraft);
        return aircraft;
    }

    @Override
    public Aircraft updateAircraft(final Aircraft aircraft) {
        if (aircraft == null) {
            throw new IllegalArgumentException("Cannot process update aircraft operation for null value argument.");
        }
        aircraftRepo.updateInstance(aircraft);
        return aircraft;
    }

    @Override
    public List<Flight> getFlights(final Aircraft aircraft) {
        return aircraft.getFlights();
    }
}
