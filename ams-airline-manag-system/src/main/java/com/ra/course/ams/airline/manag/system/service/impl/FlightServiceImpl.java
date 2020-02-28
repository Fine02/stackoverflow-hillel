package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightService;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final Repository<Flight, String> flightRepository;

    public FlightServiceImpl(Repository<Flight, String> flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightInstance> getFlightInstances(Flight flight) {
        return flight.getFlightInstances();
    }

    @Override
    public Flight add(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return flightRepository.addInstance(flight);
    }

    @Override
    public Flight update(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        flightRepository.updateInstance(flight);
        return flight;
    }

    @Override
    public boolean cancel(Flight flight) {
        if (flight == null) {
            throw new NullPointerException("Cannot process cancel operation for null value argument.");
        }
        flightRepository.removeInstance(flight);
        return true;
    }

}
