package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FlightInstanceRepository implements Repository<FlightInstance, String> {

    private final List<FlightInstance> flightInstances;

    public FlightInstanceRepository(List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
    }


    boolean isAlreadyExist(String identifier) {
        return flightInstances.stream().map(FlightInstance::getId)
                .anyMatch(number -> number.equals(identifier));
    }

    @Override
    public FlightInstance getInstance(String flightInstanceId) {
        return flightInstances.stream()
                .filter(flightInstance -> flightInstance.getId().equals(flightInstanceId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<FlightInstance> getInstances() {
        return flightInstances;
    }

    @Override
    public FlightInstance addInstance(FlightInstance flightInstance) {
        if (isAlreadyExist(flightInstance.getId())) {
            throw new InstanceAlreadyExistException();
        }
        flightInstances.add(flightInstance);
        return flightInstance;
    }

    @Override
    public void updateInstance(FlightInstance flightInstance) {
        if (!isAlreadyExist(flightInstance.getId())) {
            throw new InstanceNotExistException();
        }
        flightInstances.stream()
                .filter(flightInstanceItem -> flightInstanceItem.getId().equals(flightInstance.getId()))
                .forEach(flightInstanceItemForUpdate -> flightInstanceItemForUpdate = flightInstance);
    }

    @Override
    public void removeInstance(FlightInstance flightInstance) {
        flightInstances.removeIf(flightInstanceItem -> flightInstanceItem.getId().equals(flightInstance.getId()));
    }

}
