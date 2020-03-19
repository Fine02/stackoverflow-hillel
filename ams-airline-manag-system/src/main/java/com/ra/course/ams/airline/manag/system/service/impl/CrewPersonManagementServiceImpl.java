package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.CrewRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class CrewPersonManagementServiceImpl implements PersonManagementService<Crew> {

    private CrewRepository crewRepository;

    @Override
    public Crew findByEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        final Collection<Crew> crews = crewRepository.getInstances();
        final Crew findedCrew = crews.stream()
                .filter(crew -> email.equals(crew.getEmail()))
                .findAny()
                .orElseThrow(() -> new CrewNotExistException("No crew found for given email"));
        return new Crew(findedCrew);
    }

    @Override
    public Crew findByPhoneNumber(final String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        final Crew crew = crewRepository.getInstance(phone);
        if (crew == null) {
            throw new CrewNotExistException("No crew found for given phone number");
        }
        return new Crew(crew);
    }

    @Override
    public Crew add(final Crew crew) {
        if (crew == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo != null) {
            throw new CrewAlreadyExistException();
        }
        crewFromRepo = new Crew(crew);
        crewRepository.addInstance(crewFromRepo);
        return crew;
    }

    @Override
    public Crew updatePhone(final Crew crew, final String phone) {
        if (crew == null) {
            throw new IllegalArgumentException("Cannot proces updatePhone operation for null value argument.");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setPhone(phone);
        crewRepository.updateInstance(crewFromRepo);
        crew.setPhone(phone);
        return crew;
    }

    @Override
    public Crew updateEmail(final Crew crew, final String email) {
        if (crew == null) {
            throw new IllegalArgumentException("Cannot process updatePhone operation for null value argument.");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setEmail(email);
        crewRepository.updateInstance(crewFromRepo);
        crew.setEmail(email);
        return crew;
    }

    @Override
    public Crew updateAddress(final Crew crew, final Address address) {
        if (crew == null) {
            throw new IllegalArgumentException("Cannot process updateAddress operation for null value argument.");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setAddress(address);
        crewRepository.updateInstance(crewFromRepo);
        crew.setAddress(address);
        return crew;
    }

    @Override
    public void remove(final Crew crew) {
        if (crew == null) {
            throw new IllegalArgumentException("Cannot process remove operation for null value argument.");
        }
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewRepository.removeInstance(crewFromRepo);
    }

    public CrewRepository getCrewRepository() {
        return crewRepository;
    }

    public void setCrewRepository(final CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }
}
