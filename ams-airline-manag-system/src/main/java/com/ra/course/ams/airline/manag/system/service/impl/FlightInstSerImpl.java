package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightInstanceService;

import java.util.ArrayList;
import java.util.List;

public class FlightInstSerImpl implements FlightInstanceService {

    transient private final Repository<FlightInstance, String> flightInstRepo;

    public FlightInstSerImpl(final Repository<FlightInstance, String> flightInstRepo) {
        this.flightInstRepo = flightInstRepo;
    }

    @Override
    public FlightInstance add(final FlightInstance flightInstance) {
        if (flightInstance == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        return this.flightInstRepo.addInstance(flightInstance);
    }

    @Override
    public void updateStatus(final FlightInstance flightInstance, final FlightStatus status) {
        if (flightInstance == null || status == null) {
            throw new IllegalArgumentException("Cannot process update status operation for null value argument.");
        }
        flightInstance.setStatus(status);
        this.flightInstRepo.updateInstance(flightInstance);
    }

    @Override
    public boolean cancel(final FlightInstance flightInstance) {
        if (flightInstance == null) {
            throw new IllegalArgumentException("Cannot process cancel operation for null value argument.");
        }
        this.flightInstRepo.removeInstance(flightInstance);
        return true;
    }

    @Override
    public boolean assignPilot(final FlightInstance flightInstance, final Pilot pilot) {
        if (flightInstance == null || pilot == null) {
            throw new IllegalArgumentException("Cannot process assign pilot operation for null value argument.");
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
            throw new IllegalArgumentException("Cannot process assign crew operation for null value argument.");
        }
final         List<Crew> crews = flightInstance.getCrews() != null ? flightInstance.getCrews() : new ArrayList<>();
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
