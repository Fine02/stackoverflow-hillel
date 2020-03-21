package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PilotsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PilotManagementServiceImplTest {

    Pilot testPilot;

    @Mock
    private PilotsRepository pilotRepository;

    private PilotManagementServiceImpl pilotManagenentService;

    @BeforeEach
    public void setup() {
        testPilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        MockitoAnnotations.initMocks(this);
        pilotManagenentService = new PilotManagementServiceImpl();
        pilotManagenentService.setPilotRepo(pilotRepository);
    }

    @Test
    public void testThatAddFlightInstanceWithoutExceptions() {
        Pilot pilotInRepo =  new Pilot.Builder().setName("Ivanov Ivan").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilot = testPilot;
        FlightInstance flightInstanceToAdd = new FlightInstance();
        Pilot updatedPilot = pilotManagenentService.addFlightInstance(pilot, flightInstanceToAdd).get();

        assertThat(updatedPilot).isEqualTo(pilot);
        assertThat(updatedPilot.getFlightInstances()).hasSize(1);
        assertThat(updatedPilot.getFlightInstances().get(0)).isEqualTo(flightInstanceToAdd);
    }

    @Test
    public void testThatAddFlightInstanceThrowPilotNotExistExceptionIfNoSuchPilotFind() {
        when(pilotRepository.getInstance(any())).thenReturn(null);
        try {
            Pilot pilot = testPilot;
            FlightInstance flightInstanceToAdd = new FlightInstance();
            pilotManagenentService.addFlightInstance(pilot, flightInstanceToAdd);
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }

    @Test
    public void testThatRemoveFlightInstanceWithoutExceptions() {
        FlightInstance flightInstanceToRemove = new FlightInstance();
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan")
                .setEmail("ivanov@example.com").setPhone("11111").addFlightInstance(flightInstanceToRemove).build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilot = testPilot;
        Pilot updatedPilot = pilotManagenentService.removeFlightInstance(pilot, flightInstanceToRemove).get();

        assertThat(updatedPilot).isEqualTo(pilot);
        assertThat(updatedPilot.getFlightInstances()).isEmpty();
        assertThat(pilotInRepo.getFlightInstances()).isEmpty();
    }

    @Test
    public void testThatRemoveFlightInstanceThrowPilotNotExistExceptionIfNoSuchPilotFind() {
        when(pilotRepository.getInstance(any())).thenReturn(null);
        try {
            Pilot pilot = testPilot;
            FlightInstance flightInstanceToRemove = new FlightInstance();
            pilotManagenentService.removeFlightInstance(pilot, flightInstanceToRemove);
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }
}