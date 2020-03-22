package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.CrewRepository;
import com.ra.course.ams.airline.manag.system.service.CrewManagementService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CrewManagementServiceImpl implements CrewManagementService {

    transient private final CrewRepository crewRepository;

    public CrewManagementServiceImpl(final CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    @Override
    public Optional<Crew> addFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        addNewFlightInstance(crewFromRepo, flightInstance);
        crewRepository.updateInstance(crewFromRepo);

        addNewFlightInstance(crew, flightInstance);
        return Optional.of(crew);
    }

    private static void addNewFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances == null) {
            flightInstances = new LinkedList<>();
        }
        flightInstances.add(flightInstance);
    }

    @Override
    public Optional<Crew> removeFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        deleteFlightInstance(crewFromRepo, flightInstance);
        crewRepository.updateInstance(crewFromRepo);
        deleteFlightInstance(crew, flightInstance);
        return Optional.of(crew);
    }

    private static void deleteFlightInstance(final Crew crew, final FlightInstance flightInstance) {
        final List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances != null && !flightInstances.isEmpty()) {
            flightInstances.remove(flightInstance);
        }
    }

    public CrewRepository getCrewRepository() {
        return crewRepository;
    }

}
