package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.UserManagementService;

import java.util.Collection;

public class CrewManagenentServiceImpl implements UserManagementService<Crew> {

    private Repository<Crew, String> crewRepository;
    
    @Override
    public Crew findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Crew> crews = crewRepository.getInstances();
        return crews.stream()
                .filter(crew -> email.equals(crew.getEmail()))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Crew findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Crew crew = crewRepository.getInstance(phone);
        if (crew == null) {
            throw new InstanceNotExistException("No crew found for given phone number");
        }
        return crew;
    }

    @Override
    public Crew add(Crew crew) {
        if (crew == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return crewRepository.addInstance(crew);
    }

    @Override
    public void updateData(Crew crew) {
        if (crew == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        crewRepository.updateInstance(crew);
    }

    @Override
    public void remove(Crew crew) {
        if (crew == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        crewRepository.removeInstance(crew);
    }

    public Repository<Crew, String> getCrewRepository() {
        return crewRepository;
    }

    public void setCrewRepository(Repository<Crew, String> crewRepository) {
        this.crewRepository = crewRepository;
    }
}
