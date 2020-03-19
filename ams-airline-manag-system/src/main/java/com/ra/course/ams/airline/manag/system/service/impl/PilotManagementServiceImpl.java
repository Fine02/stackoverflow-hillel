package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PilotsRepository;
import com.ra.course.ams.airline.manag.system.service.PilotManagementService;

import java.util.LinkedList;
import java.util.List;

public class PilotManagementServiceImpl implements PilotManagementService {

    private PilotsRepository pilotRepo;

    @Override
    public Pilot addFlightInstance(final Pilot pilot, final FlightInstance flightInstance) {
        if (pilot == null || flightInstance == null) {
            throw new IllegalArgumentException("Cannot process addFlightInstanc operation with null value arguments");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        addNewFlightInstance(pilotFromRepo, flightInstance);
        pilotRepo.updateInstance(pilotFromRepo);

        addNewFlightInstance(pilot, flightInstance);
        return pilot;
    }

    private static void addNewFlightInstance(final Pilot pilot, final FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = pilot.getFlightInstances();
        if (flightInstances == null) {
            flightInstances = new LinkedList<>();
        }
        flightInstances.add(flightInstance);
    }

    @Override
    public Pilot removeFlightInstance(final Pilot pilot, final FlightInstance flightInstance) {
        if (pilot == null || flightInstance == null) {
            throw new IllegalArgumentException("Cannot process addFlightInstance operation with null value arguments");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        deleteFlightInstance(pilotFromRepo, flightInstance);
        pilotRepo.updateInstance(pilotFromRepo);

        deleteFlightInstance(pilot, flightInstance);
        return pilot;
    }

    private static void deleteFlightInstance(final Pilot pilot, final FlightInstance flightInstance) {
        final List<FlightInstance> flightInstances = pilot.getFlightInstances();
        if (flightInstances != null && !flightInstances.isEmpty()) {
            flightInstances.remove(flightInstance);
        }
    }

    public PilotsRepository getPilotRepo() {
        return pilotRepo;
    }

    public void setPilotRepo(final PilotsRepository pilotRepo) {
        this.pilotRepo = pilotRepo;
    }
}
