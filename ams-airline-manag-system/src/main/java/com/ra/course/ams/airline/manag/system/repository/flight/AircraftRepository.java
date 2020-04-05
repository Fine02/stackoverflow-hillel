package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;

import java.util.Collection;

public interface AircraftRepository {

    boolean isAlreadyExist(final String identifier);

    Aircraft getInstance(final String aircraftId);

    Collection<Aircraft> getInstances();

    Aircraft addInstance(final Aircraft aircraft);

    void updateInstance(final Aircraft aircraft);

    void removeInstance(final Aircraft aircraft);
}
