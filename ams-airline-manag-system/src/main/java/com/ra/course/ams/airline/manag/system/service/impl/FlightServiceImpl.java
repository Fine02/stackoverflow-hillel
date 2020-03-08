package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightService;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    transient private final Repository<Flight, String> flightRepository;

    public FlightServiceImpl(final Repository<Flight, String> flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightInstance> getFlightInstances(final Flight flight) {
        return flight.getFlightInstances();
    }

    @Override
    public Flight add(final Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        return flightRepository.addInstance(flight);
    }

    @Override
    public Flight update(final Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Cannot process update operation for null value argument.");
        }
        flightRepository.updateInstance(flight);
        return flight;
    }

    @Override
    public boolean cancel(final Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Cannot process cancel operation for null value argument.");
        }
        flightRepository.removeInstance(flight);
        return true;
    }

}
