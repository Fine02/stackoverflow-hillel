package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.PilotAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PilotsRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class PilotPersonManagementServiceImpl implements PersonManagementService<Pilot> {

    private PilotsRepository pilotRepo;


    @Override
    public Pilot findByEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        final Collection<Pilot> pilots = pilotRepo.getInstances();
        final Pilot findedPilot = pilots.stream()
                .filter(pilot -> email.equals(pilot.getEmail()))
                .findAny()
                .orElseThrow(() -> new PilotNotExistException("No pilot found for given email"));
        return new Pilot(findedPilot);
    }

    @Override
    public Pilot findByPhoneNumber(final String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        final Pilot pilot = pilotRepo.getInstance(phone);
        if (pilot == null) {
            throw new PilotNotExistException("No pilot found for given phone number");
        }
        return new Pilot(pilot);
    }

    @Override
    public Pilot add(final Pilot pilot) {
        if (pilot == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo != null) {
            throw new PilotAlreadyExistException();
        }
        pilotFromRepo = new Pilot(pilot);
        pilotRepo.addInstance(pilotFromRepo);
        return pilot;
    }

    @Override
    public Pilot updatePhone(final Pilot pilot, final String phone) {
        if (pilot == null) {
            throw new IllegalArgumentException("Cannot process updatePhone operation for null value argument.");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setPhone(phone);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setPhone(phone);
        return pilot;
    }

    @Override
    public Pilot updateEmail(final Pilot pilot, final String email) {
        if (pilot == null) {
            throw new IllegalArgumentException("Cannot process updateEmail operation for null value argument.");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setEmail(email);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setEmail(email);
        return pilot;
    }

    @Override
    public Pilot updateAddress(final Pilot pilot, final Address address) {
        if (pilot == null) {
            throw new IllegalArgumentException("Cannot process updateAddress operation for null value argument.");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setAddress(address);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setAddress(address);
        return pilot;
    }

    @Override
    public void remove(final Pilot pilot) {
        if (pilot == null) {
            throw new IllegalArgumentException("Cannot process remove operation for null value argument.");
        }
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotRepo.removeInstance(pilotFromRepo);
    }

    public PilotsRepository getPilotRepo() {
        return pilotRepo;
    }

    public void setPilotRepo(final PilotsRepository pilotRepo) {
        this.pilotRepo = pilotRepo;
    }
}
