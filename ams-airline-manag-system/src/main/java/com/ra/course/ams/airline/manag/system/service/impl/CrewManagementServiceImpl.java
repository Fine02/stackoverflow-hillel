package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.CrewManagementService;

import java.util.LinkedList;
import java.util.List;

public class CrewManagementServiceImpl implements CrewManagementService {

    private Repository<Crew, String> crewRepository;

    @Override
    public Crew addFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        if (crew == null || flightInstance == null) {
            throw new IllegalArgumentException("Cannot process addFlightInstance operation with null value arguments");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        addNewFlightInstance(crewFromRepo, flightInstance);
        crewRepository.updateInstance(crewFromRepo);

        addNewFlightInstance(crew, flightInstance);
        return crew;
    }

    private static void addNewFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances == null) {
            flightInstances = new LinkedList<>();
        }
        flightInstances.add(flightInstance);
    }

    @Override
    public Crew removeFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        if (crew == null || flightInstance == null) {
            throw new IllegalArgumentException("Cannot process removeFlightInstance operation with null value arguments");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        deleteFlightInstance(crewFromRepo, flightInstance);
        crewRepository.updateInstance(crewFromRepo);
        deleteFlightInstance(crew, flightInstance);
        return crew;
    }

    private static void deleteFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        final List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances != null && !flightInstances.isEmpty()) {
            flightInstances.remove(flightInstance);
        }
    }

    public Repository<Crew, String> getCrewRepository() {
        return crewRepository;
    }

    public void setCrewRepository(final Repository<Crew, String> crewRepository) {
        this.crewRepository = crewRepository;
    }
}
