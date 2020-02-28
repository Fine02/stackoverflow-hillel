package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.exceptions.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FlightInstanceRepository implements Repository<FlightInstance, String> {

    private final List<Flight> flights;

    public FlightInstanceRepository(List<Flight> flights) {
        this.flights = flights;
    }

    boolean isAlreadyExist(String identifier) {
       return flights.stream()
            .map(Flight::getFlightInstances)
            .flatMap(List::stream)
            .anyMatch(fi -> fi.getId().equals(identifier));
    }

    @Override
    public FlightInstance getInstance(String flightInstanceId) {
        return flights.stream()
                .map(Flight::getFlightInstances)
                .flatMap(List::stream)
                .filter(flightInstance -> flightInstance.getId().equals(flightInstanceId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<FlightInstance> getInstances() {
        return flights.stream()
                .map(Flight::getFlightInstances)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public FlightInstance addInstance(FlightInstance flightInstance) {
        return null; // TODO FlightInstance cannot exist without Flight
    }

    @Override
    public void updateInstance(FlightInstance flightInstance) {
        if (!isAlreadyExist(flightInstance.getId())) {
            throw new AccountNotExistException();
        }
        FlightInstance f = flights.stream()
                .map(Flight::getFlightInstances)
                .flatMap(List::stream)
                .filter(fI -> fI.getId().equals(flightInstance.getId()))
                .findFirst().orElse(null);
        if (f != null) {
            f = flightInstance;
        }
    }

    @Override
    public void removeInstance(FlightInstance flightInstance) {
        flights.stream()
                .map(Flight::getFlightInstances)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .removeIf(f -> f.getId().equals(flightInstance.getId()));
    }
}
