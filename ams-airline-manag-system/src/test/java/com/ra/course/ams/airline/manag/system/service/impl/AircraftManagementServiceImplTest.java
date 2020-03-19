package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.Aircraft;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.repository.flight.AircraftRepository;
import com.ra.course.ams.airline.manag.system.service.AircraftManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class AircraftManagementServiceImplTest {

    @Mock
    private AircraftRepository aircraftRepository;

    private AircraftManagementService aircraftManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        aircraftManagementService = new AircraftManagementServiceImpl(aircraftRepository);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentAddAircraftMethodReturnsAircraft(){
        Aircraft aircraftToAdd = new Aircraft.Builder().setId("0001").build();
        when(aircraftRepository.addInstance(aircraftToAdd)).thenReturn(aircraftToAdd);
        Aircraft returnedAircraft = aircraftManagementService.addAircraft(aircraftToAdd);

        assertThat(returnedAircraft).isNotNull();
        assertThat(returnedAircraft.getId()).isEqualTo("0001");
    }

    @Test
    public void testThatIfPassNullInArgumentAddAircraftMethodThrowsError(){
        try {
            aircraftManagementService.addAircraft(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateAircraftMethodReturnsAircraft(){
        Aircraft aircraftToUpdate = new Aircraft.Builder().setId("0001").build();
        doNothing().when(aircraftRepository).updateInstance(aircraftToUpdate);
        Aircraft returnedAircraft = aircraftManagementService.updateAircraft(aircraftToUpdate);

        assertThat(returnedAircraft).isNotNull();
        assertThat(returnedAircraft.getId()).isEqualTo("0001");
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateAircraftMethodThrowsError(){
        try {
            aircraftManagementService.updateAircraft(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidAircraftObjectArgumentInGetFlightsMethodThanReturnFlights(){
        Aircraft aircraft = new Aircraft.Builder().setId("0001").setFlights(this.getFlights()).build();
        try {
            List<Flight> returnedFlights = aircraftManagementService.getFlights(aircraft);
            assertThat(returnedFlights.size()).isEqualTo(3);
        } catch (Exception e) {
            fail("Expected that list of flights will be returned");
        }
    }

    private List<Flight> getFlights() {
        Flight[] flights = {
                new Flight.Builder().setFlightNumber("0001").build(),
                new Flight.Builder().setFlightNumber("0002").build(),
                new Flight.Builder().setFlightNumber("0003").build()
        };
        return Arrays.asList(flights);
    }
}
