package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class AircraftRepository implements Repository<Aircraft, String> {

    transient private final List<Aircraft> aircrafts;

    public AircraftRepository(final List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    public boolean isAlreadyExist(final String identifier) {
        return aircrafts.stream().map(Aircraft::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public Aircraft getInstance(final String aircraftId) {
        return aircrafts.stream()
                .filter(aircraft -> aircraft.getId().equals(aircraftId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<Aircraft> getInstances() {
        return aircrafts;
    }

    @Override
    public Aircraft addInstance(final Aircraft aircraft) {
        if (isAlreadyExist(aircraft.getId())) {
            throw new InstanceAlreadyExistException();
        }
        aircrafts.add(aircraft);
        return aircraft;
    }

    @Override
    public void updateInstance(final Aircraft aircraft) {
        if (!isAlreadyExist(aircraft.getId())) {
            throw new InstanceNotExistException();
        }
        aircrafts.stream()
                .filter(aircraftItem -> aircraftItem.getId().equals(aircraft.getId()))
                .forEach(aircrftItemForUpd -> aircrftItemForUpd = aircraft);
    }

    @Override
    public void removeInstance(final Aircraft aircraft) {
        aircrafts.removeIf(aircraftItem -> aircraftItem.getId().equals(aircraft.getId()));
    }
}
