package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface FlightRepository {

    boolean isAlreadyExist(final String identifier);

    Flight getInstance(final String flightNumber);

    Collection<Flight> getInstances();

    Flight addInstance(final Flight flight);


    void updateInstance(final Flight flight);


    void removeInstance(final Flight flight);
}
