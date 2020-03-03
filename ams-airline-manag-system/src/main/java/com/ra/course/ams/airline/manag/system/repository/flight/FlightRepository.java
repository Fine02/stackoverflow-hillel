package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class FlightRepository implements Repository<Flight, String> {

    private final List<Flight> flights;

    public FlightRepository(List<Flight> flights) {
        this.flights = flights;
    }

    boolean isAlreadyExist(String identifier) {
        return flights.stream().map(Flight::getFlightNumber)
                .anyMatch(number -> number.equals(identifier));
    }

    @Override
    public Flight getInstance(final String flightNumber) {
        return flights.stream()
                .filter(flight -> flightNumber.equals(flight.getFlightNumber()))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<Flight> getInstances() {
        return flights;
    }

    @Override
    public Flight addInstance(final Flight flight) {
        if (isAlreadyExist(flight.getFlightNumber())) {
            throw new InstanceAlreadyExistException();
        }
        flights.add(flight);
        return flight;
    }

    @Override
    public void updateInstance(Flight flight) {
        if (!isAlreadyExist(flight.getFlightNumber())) {
            throw new InstanceNotExistException();
        }
        flights.stream()
                .filter(flightItem -> flightItem.getFlightNumber().equals(flight.getFlightNumber()))
                .forEach(flightItemForUpdate -> flightItemForUpdate = flight);
    }

    @Override
    public void removeInstance(Flight flight) {
        flights.removeIf(flightItem -> flightItem.getFlightNumber().equals(flight.getFlightNumber()));
    }
}
