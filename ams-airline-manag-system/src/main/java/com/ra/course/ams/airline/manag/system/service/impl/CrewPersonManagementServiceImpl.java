package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.CrewRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CrewPersonManagementServiceImpl implements PersonManagementService<Crew> {

    private final CrewRepository crewRepository;

    public CrewPersonManagementServiceImpl(final CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    @Override
    public Optional<Crew> findByEmail(final String email) {
        final Collection<Crew> crews = crewRepository.getInstances();
        final Crew findedCrew = crews.stream()
                .filter(crew -> email.equals(crew.getEmail()))
                .findAny()
                .orElseThrow(() -> new CrewNotExistException("No crew found for given email"));
        return Optional.of(new Crew(findedCrew));
    }

    @Override
    public Optional<Crew> findByPhoneNumber(final String phone) {
        final Crew crew = crewRepository.getInstance(phone);

        return Optional.of(new Crew(crew));
    }

    @Override
    public Optional<Crew> add(final Crew crew) {
        Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo != null) {
            throw new CrewAlreadyExistException();
        }
        crewFromRepo = new Crew(crew);
        crewRepository.addInstance(crewFromRepo);
        return Optional.of(crew);
    }

    @Override
    public Optional<Crew> updatePhone(final Crew crew, final String phone) {
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setPhone(phone);
        crewRepository.updateInstance(crewFromRepo);
        crew.setPhone(phone);
        return Optional.of(crew);
    }

    @Override
    public Optional<Crew> updateEmail(final Crew crew, final String email) {
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setEmail(email);
        crewRepository.updateInstance(crewFromRepo);
        crew.setEmail(email);
        return Optional.of(crew);
    }

    @Override
    public Optional<Crew> updateAddress(final Crew crew, final Address address) {
        final Crew crewFromRepo = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepo == null) {
            throw new CrewNotExistException();
        }
        crewFromRepo.setAddress(address);
        crewRepository.updateInstance(crewFromRepo);
        crew.setAddress(address);
        return Optional.of(crew);
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

}
