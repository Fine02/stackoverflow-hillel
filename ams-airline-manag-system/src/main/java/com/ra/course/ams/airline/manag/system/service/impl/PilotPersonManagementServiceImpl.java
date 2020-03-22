package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.PilotAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PilotsRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PilotPersonManagementServiceImpl implements PersonManagementService<Pilot> {

    transient private final PilotsRepository pilotRepo;

    public PilotPersonManagementServiceImpl(final PilotsRepository pilotRepo) {
        this.pilotRepo = pilotRepo;
    }

    @Override
    public Optional<Pilot> findByEmail(final String email) {
        final Collection<Pilot> pilots = pilotRepo.getInstances();
        final Pilot findedPilot = pilots.stream()
                .filter(pilot -> email.equals(pilot.getEmail()))
                .findAny()
                .orElseThrow(() -> new PilotNotExistException("No pilot found for given email"));

        return Optional.of(new Pilot(findedPilot));
    }

    @Override
    public Optional<Pilot> findByPhoneNumber(final String phone) {
        final Pilot pilot = pilotRepo.getInstance(phone);
        if (pilot == null) {
            throw new PilotNotExistException("No pilot found for given phone number");
        }
        return Optional.of(new Pilot(pilot));
    }

    @Override
    public Optional<Pilot> add(final Pilot pilot) {
        Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo != null) {
            throw new PilotAlreadyExistException();
        }
        pilotFromRepo = new Pilot(pilot);
        pilotRepo.addInstance(pilotFromRepo);

        return Optional.of(pilot);
    }

    @Override
    public Optional<Pilot> updatePhone(final Pilot pilot, final String phone) {
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setPhone(phone);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setPhone(phone);

        return Optional.of(pilot);
    }

    @Override
    public Optional<Pilot> updateEmail(final Pilot pilot, final String email) {
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setEmail(email);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setEmail(email);

        return Optional.of(pilot);
    }

    @Override
    public Optional<Pilot> updateAddress(final Pilot pilot, final Address address) {
        final Pilot pilotFromRepo = pilotRepo.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setAddress(address);
        pilotRepo.updateInstance(pilotFromRepo);
        pilot.setAddress(address);

        return Optional.of(pilot);
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

}
