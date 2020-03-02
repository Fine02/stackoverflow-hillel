package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightInstanceService;

import java.util.ArrayList;
import java.util.List;

public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final Repository<FlightInstance, String> flightInstanceRepository;

    public FlightInstanceServiceImpl(Repository<FlightInstance, String> flightInstanceRepository) {
        this.flightInstanceRepository = flightInstanceRepository;
    }

    @Override
    public FlightInstance add(FlightInstance flightInstance) {
        if (flightInstance == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return this.flightInstanceRepository.addInstance(flightInstance);
    }

    @Override
    public void updateStatus(FlightInstance flightInstance, FlightStatus status) {
        if (flightInstance == null || status == null) {
            throw new NullPointerException("Cannot process update status operation for null value argument.");
        }
        flightInstance.setStatus(status);
        this.flightInstanceRepository.updateInstance(flightInstance);
    }

    @Override
    public boolean cancel(FlightInstance flightInstance) {
        if (flightInstance == null) {
            throw new NullPointerException("Cannot process cancel operation for null value argument.");
        }
        this.flightInstanceRepository.removeInstance(flightInstance);
        return true;
    }

    @Override
    public boolean assignPilot(FlightInstance flightInstance, Pilot pilot) {
        if (flightInstance == null || pilot == null) {
            throw new NullPointerException("Cannot process assign pilot operation for null value argument.");
        }
        List<Pilot> pilots = flightInstance.getPilots() != null ? flightInstance.getPilots() : new ArrayList<>();
        pilots.add(pilot);
        flightInstance.setPilots(pilots);
        this.flightInstanceRepository.updateInstance(flightInstance);
        return true;
    }

    @Override
    public boolean assignCrew(FlightInstance flightInstance, Crew crew) {
        if (flightInstance == null || crew == null) {
            throw new NullPointerException("Cannot process assign сrew operation for null value argument.");
        }
        List<Crew> crews = flightInstance.getCrews() != null ? flightInstance.getCrews() : new ArrayList<>();
        crews.add(crew);
        flightInstance.setCrews(crews);
        this.flightInstanceRepository.updateInstance(flightInstance);
        return true;
    }

    @Override
    public List<Pilot> getAssignedPilots(FlightInstance flightInstance) {
        return flightInstance.getPilots();
    }

    @Override
    public List<Crew> getAssignedCrew(FlightInstance flightInstance) {
        return flightInstance.getCrews();
    }
}
