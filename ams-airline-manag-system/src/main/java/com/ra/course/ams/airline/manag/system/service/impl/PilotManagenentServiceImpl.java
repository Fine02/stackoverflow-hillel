package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.UserManagementService;

import java.util.Collection;

public class PilotManagenentServiceImpl implements UserManagementService<Pilot> {

    private Repository<Pilot, String> pilotRepository;
    
    @Override
    public Pilot findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Pilot> pilots = pilotRepository.getInstances();
        return pilots.stream()
                .filter(pilot -> email.equals(pilot.getEmail()))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Pilot findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Pilot pilot = pilotRepository.getInstance(phone);
        if (pilot == null) {
            throw new InstanceNotExistException("No pilot found for given phone number");
        }
        return pilot;
    }

    @Override
    public Pilot add(Pilot pilot) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return pilotRepository.addInstance(pilot);
    }

    @Override
    public void updateData(Pilot pilot) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        pilotRepository.updateInstance(pilot);
    }

    @Override
    public void remove(Pilot pilot) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        pilotRepository.removeInstance(pilot);
    }

    public Repository<Pilot, String> getPilotRepository() {
        return pilotRepository;
    }

    public void setPilotRepository(Repository<Pilot, String> pilotRepository) {
        this.pilotRepository = pilotRepository;
    }
}
