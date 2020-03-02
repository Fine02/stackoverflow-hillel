package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exceptions.CrewAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.CrewManagementService;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CrewManagenentServiceImpl implements PersonManagementService<Crew>, CrewManagementService {

    private Repository<Crew, String> crewRepository;

    @Override
    public Crew addFlightInstance(Crew crew, FlightInstance flightInstance) {
        if (crew == null || flightInstance == null) {
            throw new NullPointerException("Cannot process addFlightInstance operation with null value arguments");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        addNewFlightInstance(crewFromRepository, flightInstance);
        crewRepository.updateInstance(crewFromRepository);

        addNewFlightInstance(crew, flightInstance);
        return crew;
    }

    private static void addNewFlightInstance(Crew crew, FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances == null) {
            flightInstances = new LinkedList<>();
        }
        flightInstances.add(flightInstance);
    }

    @Override
    public Crew removeFlightInstance(Crew crew, FlightInstance flightInstance) {
        if (crew == null || flightInstance == null) {
            throw new NullPointerException("Cannot process removeFlightInstance operation with null value arguments");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        deleteFlightInstance(crewFromRepository, flightInstance);
        crewRepository.updateInstance(crewFromRepository);
        deleteFlightInstance(crew, flightInstance);
        return crew;
    }

    private static void deleteFlightInstance(Crew crew, FlightInstance flightInstance) {
        List<FlightInstance> flightInstances = crew.getFlightInstances();
        if (flightInstances != null && !flightInstances.isEmpty()) {
            flightInstances.remove(flightInstance);
        }
    }

    @Override
    public Crew findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Crew> crews = crewRepository.getInstances();
        Crew findedCrew = crews.stream()
                .filter(crew -> email.equals(crew.getEmail()))
                .findAny()
                .orElseThrow(() -> new CrewNotExistException("No crew found for given email"));
        return new Crew(findedCrew);
    }

    @Override
    public Crew findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Crew crew = crewRepository.getInstance(phone);
        if (crew == null) {
            throw new CrewNotExistException("No crew found for given phone number");
        }
        return new Crew(crew);
    }

    @Override
    public Crew add(Crew crew) {
        if (crew == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
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
    public Crew updatePhone(Crew crew, String phone) {
        if (crew == null) {
            throw new NullPointerException("Cannot process updatePhone operation for null value argument.");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        crewFromRepository.setPhone(phone);
        crewRepository.updateInstance(crewFromRepository);
        crew.setPhone(phone);
        return crew;
    }

    @Override
    public Crew updateEmail(Crew crew, String email) {
        if (crew == null) {
            throw new NullPointerException("Cannot process updatePhone operation for null value argument.");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        crewFromRepository.setEmail(email);
        crewRepository.updateInstance(crewFromRepository);
        crew.setEmail(email);
        return crew;
    }

    @Override
    public Crew updateAddress(Crew crew, Address address) {
        if (crew == null) {
            throw new NullPointerException("Cannot process updateAddress operation for null value argument.");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        crewFromRepository.setAddress(address);
        crewRepository.updateInstance(crewFromRepository);
        crew.setAddress(address);
        return crew;
    }

    @Override
    public void remove(Crew crew) {
        if (crew == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        Crew crewFromRepository = crewRepository.getInstance(crew.getPhone());
        if (crewFromRepository == null) {
            throw new CrewNotExistException();
        }
        crewRepository.removeInstance(crewFromRepository);
    }

    public Repository<Crew, String> getCrewRepository() {
        return crewRepository;
    }

    public void setCrewRepository(Repository<Crew, String> crewRepository) {
        this.crewRepository = crewRepository;
    }
}
