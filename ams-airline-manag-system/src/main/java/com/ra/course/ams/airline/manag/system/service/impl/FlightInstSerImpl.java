package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightInstanceRepository;
import com.ra.course.ams.airline.manag.system.service.FlightInstanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightInstSerImpl implements FlightInstanceService {

    transient private final FlightInstanceRepository flightInstRepo;

    public FlightInstSerImpl(final FlightInstanceRepository flightInstRepo) {
        this.flightInstRepo = flightInstRepo;
    }

    @Override
    public Optional<FlightInstance> add(final FlightInstance flightInstance) {

        return Optional.of(this.flightInstRepo.addInstance(flightInstance));
    }

    @Override
    public void updateStatus(final FlightInstance flightInstance, final FlightStatus status) {
        flightInstance.setStatus(status);
        this.flightInstRepo.updateInstance(flightInstance);
    }

    @Override
    public boolean cancel(final FlightInstance flightInstance) {
        if (flightInstance == null) {
            return false;
        }
        this.flightInstRepo.removeInstance(flightInstance);
        return true;
    }

    @Override
    public boolean assignPilot(final FlightInstance flightInstance, final Pilot pilot) {
        if (flightInstance == null || pilot == null) {
            return false;
        }
        final List<Pilot> pilots = flightInstance.getPilots() != null ? flightInstance.getPilots() : new ArrayList<>();
        pilots.add(pilot);
        flightInstance.setPilots(pilots);
        this.flightInstRepo.updateInstance(flightInstance);
        return true;
    }

    @Override
    public boolean assignCrew(final FlightInstance flightInstance, final Crew crew) {
        if (flightInstance == null || crew == null) {
            return false;
        }
        final List<Crew> crews = flightInstance.getCrews() != null ? flightInstance.getCrews() : new ArrayList<>();
        crews.add(crew);
        flightInstance.setCrews(crews);
        this.flightInstRepo.updateInstance(flightInstance);
        return true;
    }

    @Override
    public List<Pilot> getAssignedPilots(final FlightInstance flightInstance) {
        return flightInstance.getPilots();
    }

    @Override
    public List<Crew> getAssignedCrew(final FlightInstance flightInstance) {
        return flightInstance.getCrews();
    }
}
