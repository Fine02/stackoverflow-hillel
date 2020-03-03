package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exceptions.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PilotAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;
import com.ra.course.ams.airline.manag.system.service.PilotManagementService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PilotManagementServiceImpl implements PersonManagementService<Pilot>, PilotManagementService {

    private Repository<Pilot, String> pilotRepository;


    @Override
    public Pilot findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Pilot> pilots = pilotRepository.getInstances();
        Pilot findedPilot = pilots.stream()
                .filter(pilot -> email.equals(pilot.getEmail()))
                .findAny()
                .orElseThrow(() -> new PilotNotExistException("No pilot found for given email"));
        return new Pilot(findedPilot);
    }

    @Override
    public Pilot findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Pilot pilot = pilotRepository.getInstance(phone);
        if (pilot == null) {
            throw new PilotNotExistException("No pilot found for given phone number");
        }
        return new Pilot(pilot);
    }

    @Override
    public Pilot add(Pilot pilot) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepo != null) {
            throw new PilotAlreadyExistException();
        }
        pilotFromRepo = new Pilot(pilot);
        pilotRepository.addInstance(pilotFromRepo);
        return pilot;
    }

    @Override
    public Pilot updatePhone(Pilot pilot, String phone) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process updatePhone operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setPhone(phone);
        pilotRepository.updateInstance(pilotFromRepo);
        pilot.setPhone(phone);
        return pilot;
    }

    @Override
    public Pilot updateEmail(Pilot pilot, String email) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process updateEmail operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setEmail(email);
        pilotRepository.updateInstance(pilotFromRepo);
        pilot.setEmail(email);
        return pilot;
    }

    @Override
    public Pilot updateAddress(Pilot pilot, Address address) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process updateAddress operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotFromRepo.setAddress(address);
        pilotRepository.updateInstance(pilotFromRepo);
        pilot.setAddress(address);
        return pilot;
    }

    @Override
    public void remove(Pilot pilot) {
        if (pilot == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        Pilot pilotFromRepo = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepo == null) {
            throw new PilotNotExistException();
        }
        pilotRepository.removeInstance(pilotFromRepo);
    }

    @Override
    public Pilot addFlightInstance(Pilot pilot, FlightInstance flightInstance) {
        if (pilot == null || flightInstance == null) {
            throw new NullPointerException("Cannot process addFlightInstance operation with null value arguments");
        }
        Pilot pilotFromRepository = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepository == null) {
            throw new PilotNotExistException();
        }
        addNewFlightInstance(pilotFromRepository, flightInstance);
        pilotRepository.updateInstance(pilotFromRepository);

        addNewFlightInstance(pilot, flightInstance);
        return pilot;
    }

    private static void addNewFlightInstance(Pilot pilot, FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = pilot.getFlightInstances();
        if (flightInstances == null) {
            flightInstances = new LinkedList<>();
        }
        flightInstances.add(flightInstance);
    }

    @Override
    public Pilot removeFlightInstance(Pilot pilot, FlightInstance flightInstance) {
        if (pilot == null || flightInstance == null) {
            throw new NullPointerException("Cannot process addFlightInstance operation with null value arguments");
        }
        Pilot pilotFromRepository = pilotRepository.getInstance(pilot.getPhone());
        if (pilotFromRepository == null) {
            throw new PilotNotExistException();
        }
        deleteFlightInstance(pilotFromRepository, flightInstance);
        pilotRepository.updateInstance(pilotFromRepository);

        deleteFlightInstance(pilot, flightInstance);
        return pilot;
    }

    private static void deleteFlightInstance(Pilot pilot, FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = pilot.getFlightInstances();
        if (flightInstances != null && !flightInstances.isEmpty()) {
            flightInstances.remove(flightInstance);
        }
    }

    public Repository<Pilot, String> getPilotRepository() {
        return pilotRepository;
    }

    public void setPilotRepository(Repository<Pilot, String> pilotRepository) {
        this.pilotRepository = pilotRepository;
    }
}
