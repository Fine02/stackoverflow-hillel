package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.flight.CustomScheduleRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.WeeklyScheduleRepository;
import com.ra.course.ams.airline.manag.system.service.FlightScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class FlightScheduleServiceImplTest {

    private Flight testFlight;

    @Mock
    private WeeklyScheduleRepository weeklyScheduleRepository;
    @Mock
    private CustomScheduleRepository customScheduleRepository;

    private FlightScheduleService flightScheduleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testFlight = new Flight.Builder().setFlightNumber("0001").build();
        flightScheduleService = new FlightScheduleServiceImpl(weeklyScheduleRepository, customScheduleRepository);
    }

    @Test
    public void testThatIfPassValidWeeklyScheduleObjectInArgumentAddScheduleMethodReturnsWeeklySchedule(){
        WeeklySchedule weeklyScheduleToAdd = new WeeklySchedule.Builder().setFlight(testFlight).build();
        when(weeklyScheduleRepository.addInstance(weeklyScheduleToAdd)).thenReturn(weeklyScheduleToAdd);
        Optional <WeeklySchedule> returnedWeeklySchedule = flightScheduleService.addSchedule(weeklyScheduleToAdd);

        assertThat(returnedWeeklySchedule.get().getFlight()).isEqualTo(testFlight);
    }

    @Test
    public void testThatIfPassValidCustomScheduleObjectInArgumentAddScheduleMethodReturnsWeeklySchedule(){
        CustomSchedule customScheduleToAdd = new CustomSchedule.Builder().setFlight(testFlight).build();
        when(customScheduleRepository.addInstance(customScheduleToAdd)).thenReturn(customScheduleToAdd);
        CustomSchedule returnedCustomSchedule = flightScheduleService.addSchedule(customScheduleToAdd).get();

        assertThat(returnedCustomSchedule).isNotNull();
        assertThat(returnedCustomSchedule.getFlight()).isEqualTo(testFlight);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateWeeklyScheduleMethodReturnsWeeklySchedule(){
        WeeklySchedule weeklyScheduleToUpdate = new WeeklySchedule.Builder().setFlight(testFlight).build();
        doNothing().when(weeklyScheduleRepository).updateInstance(weeklyScheduleToUpdate);
        WeeklySchedule returnedWeeklySchedule = flightScheduleService.updateSchedule(weeklyScheduleToUpdate).get();

        assertThat(returnedWeeklySchedule).isNotNull();
        assertThat(returnedWeeklySchedule.getFlight()).isEqualTo(testFlight);
    }

    @Test
    public void testThatIfPassValidObjectInArgumentUpdateCustomScheduleMethodReturnsCustomSchedule(){
        CustomSchedule customScheduleToUpdate = new CustomSchedule.Builder().setFlight(testFlight).build();
        doNothing().when(customScheduleRepository).updateInstance(customScheduleToUpdate);
        CustomSchedule returnedCustomSchedule = flightScheduleService.updateSchedule(customScheduleToUpdate).get();

        assertThat(returnedCustomSchedule).isNotNull();
        assertThat(returnedCustomSchedule.getFlight()).isEqualTo(testFlight);
    }

    private WeeklySchedule returnWeeklyScheduleNull() {
        return null;
    }

    private CustomSchedule returnCustomScheduleNull() {
        return null;
    }
}
