package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Flight;

import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class FlightServiceImplTest {

    private Flight flightToAdd;

    @Mock
    private Repository<Flight, String> flightRepository;

    private FlightService flightService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        flightService = new FlightServiceImpl(flightRepository);
        flightToAdd = new Flight.Builder().setFlightNumber("0001").build();
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentAddMethodReturnsFlight() {
        when(flightRepository.addInstance(any(Flight.class))).thenReturn(flightToAdd);
        Flight returnedFlight = flightService.add(flightToAdd);

        assertThat(returnedFlight).isNotNull();
        assertThat(returnedFlight.getFlightNumber()).isEqualTo("0001");
    }

    @Test
    public void testThatIfPassNullInArgumentAddMethodThrowsError() {
        try {
            flightService.add(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentUpdateMethodReturnsFlight() {
        doNothing().when(flightRepository).updateInstance(any(Flight.class));
        Flight returnedFlight = flightService.update(flightToAdd);

        assertThat(returnedFlight).isNotNull();
        assertThat(returnedFlight.getFlightNumber()).isEqualTo("0001");
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateMethodThrowsError() {
        try {
            flightService.update(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentCancelMethodReturnsTrue() {
        doNothing().when(flightRepository).removeInstance(any(Flight.class));
        boolean result = flightService.cancel(flightToAdd);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testThatIfPassNullInArgumentCancelMethodThrowsError() {
        try {
            flightService.cancel(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
