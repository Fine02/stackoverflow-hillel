package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightInstanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class FlightInstanceServiceImplTest {

    FlightInstance testFlightInst;

    @Mock
    private Repository<FlightInstance, String> flightInstanceRepository;

    private FlightInstanceService flightInstanceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testFlightInst = new FlightInstance.Builder().setId("0001").build();
        flightInstanceService = new FlightInstSerImpl(flightInstanceRepository);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentAddMethodReturnsFlightInstance() {
        FlightInstance flightInstanceToAdd = testFlightInst;
        when(flightInstanceRepository.addInstance(flightInstanceToAdd)).thenReturn(flightInstanceToAdd);
        FlightInstance returnedFlightInstance = flightInstanceService.add(flightInstanceToAdd);

        assertThat(returnedFlightInstance).isNotNull();
        assertThat(returnedFlightInstance.getId()).isEqualTo("0001");
    }

    @Test
    public void testThatIfPassNullInArgumentAddMethodThrowsError() {
        try {
            flightInstanceService.add(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateMethodReturnsFlightNoErrorThrows() {
        FlightInstance flightInstanceToUpdate = testFlightInst;
        doNothing().when(flightInstanceRepository).updateInstance(flightInstanceToUpdate);
        try {
            flightInstanceService.updateStatus(flightInstanceToUpdate, FlightStatus.ACTIVE);
        } catch (Exception e) {
            fail("Expected that no error will be throws");
        }
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateMethodThrowsError() {
        try {
            flightInstanceService.updateStatus(null, FlightStatus.ACTIVE);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void whenUpdateStatusWithFlightStatusNullThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightInstanceService.updateStatus(new FlightInstance(), null));
    }

    @Test
    public void testThatIfPassValidObjectInArgumentCancelMethodReturnsTrue() {
        FlightInstance flightInstanceToCancel = testFlightInst;
        doNothing().when(flightInstanceRepository).removeInstance(flightInstanceToCancel);
        boolean result = flightInstanceService.cancel(flightInstanceToCancel);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testThatIfPassNullInArgumentCancelMethodThrowsError() {
        try {
            flightInstanceService.cancel(null);
            fail("Expected that IllegalArgumentException will be throws");
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatIfPassValidObjectInArgumentAssignPilotMethodCallRepositoryWithRightObjectAndReturnsTrue() {
        FlightInstance flightInstanceToUpdate = testFlightInst;
        doNothing().when(flightInstanceRepository).updateInstance(flightInstanceToUpdate);
        Pilot pilot = new Pilot();
        try {
            flightInstanceService.assignPilot(flightInstanceToUpdate, pilot);
        } catch (Exception e) {
            fail("Expected that no error will be throws");
        }
    }

    @Test
    public void testThatIfPassValidObjectInArgumentAssignCrewMethodCallRepositoryWithRightObjectAndReturnsTrue() {
        FlightInstance flightInstanceToUpdate = testFlightInst;
        doNothing().when(flightInstanceRepository).updateInstance(flightInstanceToUpdate);
        Crew crew = new Crew();
        try {
            flightInstanceService.assignCrew(flightInstanceToUpdate, crew);
        } catch (Exception e) {
            fail("Expected that no error will be throws");
        }
    }

    @Test
    public void testThatIfPassValidObjectArgumentInGetAssignedPilotsMethodThanReturnPilots() {
        FlightInstance flightInstance = new FlightInstance.Builder().setId("0001").setPilots(Arrays.asList(new Pilot[]{new Pilot(), new Pilot(), new Pilot()})).build();
        try {
            List<Pilot> returnedPilots = flightInstanceService.getAssignedPilots(flightInstance);
            assertThat(returnedPilots.size()).isEqualTo(3);
        } catch (Exception e) {
            fail("Expected that list of flights will be returned");
        }
    }

    @Test
    public void testThatIfPassValidObjectArgumentInGetAssignedCrewsMethodThanReturnCrews() {
        FlightInstance flightInstance = new FlightInstance.Builder().setId("0001").setCrews(Arrays.asList(new Crew[]{new Crew(), new Crew(), new Crew()})).build();
        try {
            List<Crew> returnedCrews = flightInstanceService.getAssignedCrew(flightInstance);
            assertThat(returnedCrews.size()).isEqualTo(3);
        } catch (Exception e) {
            fail("Expected that list of flights will be returned");
        }
    }

    @Test
    public void whenAssignPilotWithflightInstanceNullThenThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightInstanceService.assignPilot(null, new Pilot()));
    }

    @Test
    public void whenAssignCrewWithCrewNullThenThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightInstanceService.assignCrew(new FlightInstance(), null));
    }

    @Test
    public void whenAssignCrewWithflightInstanceNullThenThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightInstanceService.assignCrew(null, new Crew()));
    }

    @Test
    public void whenAssignPilotWithPilotNullThenThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightInstanceService.assignPilot(new FlightInstance(), null));
    }
}
