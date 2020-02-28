package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class AircraftRepository implements Repository<Aircraft, String> {

    private final List<Aircraft> aircrafts;

    public AircraftRepository(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    boolean isAlreadyExist(String identifier) {
        return aircrafts.stream().map(Aircraft::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public Aircraft getInstance(String aircraftId) {
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
    public Aircraft addInstance(Aircraft aircraft) {
        if (isAlreadyExist(aircraft.getId())) {
            throw new InstanceAlreadyExistException();
        }
        aircrafts.add(aircraft);
        return aircraft;
    }

    @Override
    public void updateInstance(Aircraft aircraft) {
        if (!isAlreadyExist(aircraft.getId())) {
            throw new InstanceNotExistException();
        }
        aircrafts.stream()
                .filter(aircraftItem -> aircraftItem.getId().equals(aircraft.getId()))
                .forEach(aircraftItemForUpdate -> aircraftItemForUpdate = aircraft);
    }

    @Override
    public void removeInstance(Aircraft aircraft) {
        aircrafts.removeIf(aircraftItem -> aircraftItem.getId().equals(aircraft.getId()));
    }
}
