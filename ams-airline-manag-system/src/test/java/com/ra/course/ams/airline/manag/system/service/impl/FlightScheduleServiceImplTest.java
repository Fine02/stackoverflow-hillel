package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class FlightScheduleServiceImplTest {

    @Mock
    private Repository<WeeklySchedule, String> weeklyScheduleRepository;
    @Mock
    private Repository<CustomSchedule, String> customScheduleRepository;

    private FlightScheduleService flightScheduleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        flightScheduleService = new FlightScheduleServiceImpl(weeklyScheduleRepository, customScheduleRepository);
    }

    @Test
    public void testThatIfPassValidWeeklyScheduleObjectInArgumentAddScheduleMethodReturnsWeeklySchedule(){
        Flight testFlight = new Flight.Builder().setFlightNumber("0001").build();
        WeeklySchedule weeklyScheduleToAdd = new WeeklySchedule.Builder().setFlight(testFlight).build();
        when(weeklyScheduleRepository.addInstance(weeklyScheduleToAdd)).thenReturn(weeklyScheduleToAdd);
        WeeklySchedule returnedWeeklySchedule = flightScheduleService.addSchedule(weeklyScheduleToAdd);

        assertThat(returnedWeeklySchedule).isNotNull();
        assertThat(returnedWeeklySchedule.getFlight()).isEqualTo(testFlight);

        verify(weeklyScheduleRepository, times(1)).addInstance(weeklyScheduleToAdd);
    }

    @Test
    public void testThatIfPassNullInArgumentAddWeeklyScheduleMethodThrowsError(){
        try {
            flightScheduleService.addSchedule(this.returnWeeklyScheduleNull());
            fail("Expected that NullPointerException will be throws");
        } catch (NullPointerException e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
        verifyZeroInteractions(weeklyScheduleRepository);
    }

    @Test
    public void testThatIfPassValidCustomScheduleObjectInArgumentAddScheduleMethodReturnsWeeklySchedule(){
        Flight testFlight = new Flight.Builder().setFlightNumber("0001").build();
        CustomSchedule customScheduleToAdd = new CustomSchedule.Builder().setFlight(testFlight).build();
        when(customScheduleRepository.addInstance(customScheduleToAdd)).thenReturn(customScheduleToAdd);
        CustomSchedule returnedCustomSchedule = flightScheduleService.addSchedule(customScheduleToAdd);

        assertThat(returnedCustomSchedule).isNotNull();
        assertThat(returnedCustomSchedule.getFlight()).isEqualTo(testFlight);

        verify(customScheduleRepository, times(1)).addInstance(customScheduleToAdd);
    }

    @Test
    public void testThatIfPassNullInArgumentAddCustomScheduleMethodThrowsError(){
        try {
            flightScheduleService.addSchedule(this.returnCustomScheduleNull());
            fail("Expected that NullPointerException will be throws");
        } catch (NullPointerException e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
        verifyZeroInteractions(customScheduleRepository);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateWeeklyScheduleMethodReturnsWeeklySchedule(){
        Flight testFlight = new Flight.Builder().setFlightNumber("0001").build();
        WeeklySchedule weeklyScheduleToUpdate = new WeeklySchedule.Builder().setFlight(testFlight).build();
        doNothing().when(weeklyScheduleRepository).updateInstance(weeklyScheduleToUpdate);
        WeeklySchedule returnedWeeklySchedule = flightScheduleService.updateSchedule(weeklyScheduleToUpdate);

        assertThat(returnedWeeklySchedule).isNotNull();
        assertThat(returnedWeeklySchedule.getFlight()).isEqualTo(testFlight);

        verify(weeklyScheduleRepository, times(1)).updateInstance(weeklyScheduleToUpdate);
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateWeeklyScheduleMethodThrowsError(){
        try {
            flightScheduleService.updateSchedule(this.returnWeeklyScheduleNull());
            fail("Expected that NullPointerException will be throws");
        } catch (NullPointerException e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
        verifyZeroInteractions(weeklyScheduleRepository);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateCustomScheduleMethodReturnsCustomSchedule(){
        Flight testFlight = new Flight.Builder().setFlightNumber("0001").build();
        CustomSchedule customScheduleToUpdate = new CustomSchedule.Builder().setFlight(testFlight).build();
        doNothing().when(customScheduleRepository).updateInstance(customScheduleToUpdate);
        CustomSchedule returnedCustomSchedule = flightScheduleService.updateSchedule(customScheduleToUpdate);

        assertThat(returnedCustomSchedule).isNotNull();
        assertThat(returnedCustomSchedule.getFlight()).isEqualTo(testFlight);

        verify(customScheduleRepository, times(1)).updateInstance(customScheduleToUpdate);
    }

    @Test
    public void testThatIfPassNullInArgumentUpdateCustomScheduleMethodThrowsError(){
        try {
            flightScheduleService.updateSchedule(this.returnCustomScheduleNull());
            fail("Expected that NullPointerException will be throws");
        } catch (NullPointerException e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
        verifyZeroInteractions(customScheduleRepository);
    }

    private WeeklySchedule returnWeeklyScheduleNull() {
        return null;
    }

    private CustomSchedule returnCustomScheduleNull() {
        return null;
    }
}
