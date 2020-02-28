package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightRepository;
import com.ra.course.ams.airline.manag.system.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

//import static org.assertj.core.api.Assertions.assertThat;

public class FlightServiceTest {

    private FlightService flightService;
    private FlightRepository flightRepository = new FlightRepository(new ArrayList<Flight>());

    @BeforeEach
    public void insertMock() {
        flightService = new FlightServiceImpl(flightRepository);
    }

    public void whenAddNewFlightThatNotExistThenNoErrorThrows() {
        Flight notExistedFlight = new Flight.Builder().build();
        try {
            Flight returnedFlight = flightService.add(notExistedFlight);
//            assetThat()
        } catch (InstanceAlreadyExistException e) {

        }
    }

    public void whenAddNewFlightThatExistThenErrorThrows() {

    }

}
