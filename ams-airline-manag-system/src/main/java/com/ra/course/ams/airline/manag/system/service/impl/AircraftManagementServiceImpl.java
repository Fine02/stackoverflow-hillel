package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.repository.flight.AircraftRepository;
import com.ra.course.ams.airline.manag.system.service.AircraftManagementService;

import java.util.List;
import java.util.Optional;

public class AircraftManagementServiceImpl implements AircraftManagementService {

    transient private final AircraftRepository aircraftRepo;

    public AircraftManagementServiceImpl(final AircraftRepository aircraftRepo) {
        this.aircraftRepo = aircraftRepo;
    }

    @Override
    public Optional <Aircraft> addAircraft(final Aircraft aircraft) {
        aircraftRepo.addInstance(aircraft);

        return Optional.of(aircraft);
    }

    @Override
    public Optional <Aircraft> updateAircraft(final Aircraft aircraft) {
        aircraftRepo.updateInstance(aircraft);

        return Optional.of(aircraft);
    }

    @Override
    public Optional <List<Flight>> getFlights(final Aircraft aircraft) {
        return Optional.of(aircraft.getFlights());
    }
}
