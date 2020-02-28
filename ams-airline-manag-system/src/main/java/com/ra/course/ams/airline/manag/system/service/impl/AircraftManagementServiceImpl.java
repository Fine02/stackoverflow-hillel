package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.AircraftManagmentService;

import java.util.List;

public class AircraftManagmentServiceImpl implements AircraftManagmentService {

    private final Repository<Aircraft, String> aircraftRepository;

    public AircraftManagmentServiceImpl(Repository<Aircraft, String> aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public Aircraft addAircraft(Aircraft aircraft) {
        aircraftRepository.addInstance(aircraft);
        return aircraft;
    }

    @Override
    public Aircraft updateAircraft(Aircraft aircraft) {
        aircraftRepository.updateInstance(aircraft);
        return aircraft;
    }

    @Override
    public List<Flight> getFlights(Aircraft aircraft) {
        return aircraft.getFlights();
    }
}
