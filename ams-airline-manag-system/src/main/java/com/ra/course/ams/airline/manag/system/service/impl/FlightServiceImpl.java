package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightRepository;
import com.ra.course.ams.airline.manag.system.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    transient private final FlightRepository flightRepository;

    public FlightServiceImpl(final FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightInstance> getFlightInstances(final Flight flight) {

        return flight.getFlightInstances();
    }

    @Override
    public Optional<Flight> add(final Flight flight) {

        return Optional.of(flightRepository.addInstance(flight));
    }

    @Override
    public Optional<Flight> update(final Flight flight) {
        flightRepository.updateInstance(flight);

        return Optional.of(flight);
    }

    @Override
    public boolean cancel(final Flight flight) {
        if (flight == null) {
            return false;
        }
        flightRepository.removeInstance(flight);
        return true;
    }
}
