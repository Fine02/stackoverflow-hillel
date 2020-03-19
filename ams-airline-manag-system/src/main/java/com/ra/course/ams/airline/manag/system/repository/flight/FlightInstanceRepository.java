package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;

import java.util.Collection;

public interface FlightInstanceRepository {

    boolean isAlreadyExist(final String identifier);

    FlightInstance getInstance(final String flightInstanceId);

    Collection<FlightInstance> getInstances();

    FlightInstance addInstance(final FlightInstance flightInstance);

    void updateInstance(final FlightInstance flightInstance);

    void removeInstance(final FlightInstance flightInstance);
}
