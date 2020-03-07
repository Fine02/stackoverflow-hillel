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

    @Mock
    private Repository<Flight, String> flightRepository;

    private FlightService flightService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        flightRepository = new FlightRepository(new ArrayList<>());
        flightService = new FlightServiceImpl(flightRepository);
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentAddMethodReturnsFlight(){
        Flight flightToAdd = new Flight.Builder().setFlightNumber("0001").build();
        when(flightRepository.addInstance(any(Flight.class))).thenReturn(flightToAdd);
        Flight returnedFlight = flightService.add(flightToAdd);

        assertThat(returnedFlight).isNotNull();
        assertThat(returnedFlight.getFlightNumber()).isEqualTo("0001");

        verify(flightRepository, times(1)).addInstance(flightToAdd);
    }

    @Test
    public void testThatIfPassNullInArgumentAddMethodThrowsError(){
        try {
            flightService.add(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(flightRepository);
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentUpdateMethodReturnsFlight(){
        Flight flightToAdd = new Flight.Builder().setFlightNumber("0001").build();
        doNothing().when(flightRepository).updateInstance(any(Flight.class));
        Flight returnedFlight = flightService.update(flightToAdd);

        assertThat(returnedFlight).isNotNull();
        assertThat(returnedFlight.getFlightNumber()).isEqualTo("0001");

        verify(flightRepository, times(1)).updateInstance(flightToAdd);
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateMethodThrowsError(){
        try {
            flightService.update(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(flightRepository);
    }

    @Test
    public void testThatIfPassValidFlightObjectInArgumentCancelMethodReturnsTrue(){
        Flight flightToAdd = new Flight.Builder().setFlightNumber("0001").build();
        doNothing().when(flightRepository).removeInstance(any(Flight.class));
        boolean result = flightService.cancel(flightToAdd);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);

        verify(flightRepository, times(1)).removeInstance(flightToAdd);
    }

    @Test
    public void testThatIfPassNullInArgumentCancelMethodThrowsError(){
        try {
            flightService.cancel(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(flightRepository);
    }

}
