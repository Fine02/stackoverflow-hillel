package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class FlightInstanceRepository implements Repository<FlightInstance, String> {

    transient private final List<FlightInstance> flightInstances;

    public FlightInstanceRepository(final List<FlightInstance> flightInstances) {
        this.flightInstances = flightInstances;
    }


    public boolean isAlreadyExist(final String identifier) {
        return flightInstances.stream().map(FlightInstance::getId)
                .anyMatch(number -> number.equals(identifier));
    }

    @Override
    public FlightInstance getInstance(final String flightInstanceId) {
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
    public FlightInstance addInstance(final FlightInstance flightInstance) {
        if (isAlreadyExist(flightInstance.getId())) {
            throw new InstanceAlreadyExistException();
        }
        flightInstances.add(flightInstance);
        return flightInstance;
    }

    @Override
    public void updateInstance(final FlightInstance flightInstance) {
        if (!isAlreadyExist(flightInstance.getId())) {
            throw new InstanceNotExistException();
        }
        flightInstances.stream()
                .filter(flightInstItem -> flightInstItem.getId().equals(flightInstance.getId()))
                .forEach(flighInstItForUp -> flighInstItForUp = flightInstance);
    }

    @Override
    public void removeInstance(final FlightInstance flightInstance) {
        flightInstances.removeIf(flightInstItem -> flightInstItem.getId().equals(flightInstance.getId()));
    }
}
