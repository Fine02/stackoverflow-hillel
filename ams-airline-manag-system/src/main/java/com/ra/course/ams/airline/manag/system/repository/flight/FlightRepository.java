package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.exceptions.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.AccountNotExistException;
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
                .allMatch(number -> number.equals(identifier));
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
            throw new AccountAlreadyExistException();
        }
        flights.add(flight);
        return flight;
    }

    @Override
    public void updateInstance(Flight flight) {
        if (!isAlreadyExist(flight.getFlightNumber())) {
            throw new AccountNotExistException();
        }
        Flight flightToUpdate = flights.stream()
                .filter(f -> f.getFlightNumber().equals(flight.getFlightNumber()))
                .findFirst().orElse(null);
        if (flightToUpdate != null) {
            flightToUpdate = flight;
        }
    }

    @Override
    public void removeInstance(Flight flight) {
        flights.removeIf(f -> f.getFlightNumber().equals(flight.getFlightNumber()));
    }
}
