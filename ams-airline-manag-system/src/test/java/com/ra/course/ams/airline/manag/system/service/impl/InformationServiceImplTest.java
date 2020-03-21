package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.exception.ScheduleNotExistException;
import com.ra.course.ams.airline.manag.system.repository.flight.CustomScheduleRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightInstanceRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.FlightRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.WeeklyScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InformationServiceImplTest {

    private InformationServiceImpl informationService;
    private Airport airport;
    private Time time;

    @Mock
    private CustomScheduleRepository customScheduleRepo;
    @Mock
    private WeeklyScheduleRepository weeklyScheduleRepo;
    @Mock
    private FlightInstanceRepository flightInstRepo;
    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    public void setUp() {
        airport = new Airport();
        time = new Time(1);

        MockitoAnnotations.initMocks(this);
        informationService = new InformationServiceImpl();
        informationService.setFligRepos(flightInstRepo, flightRepository);
        informationService.setShedRepos(weeklyScheduleRepo, customScheduleRepo);
    }

    @Test
    public void whenFlightNumberIsActualThenCheckFlightWeeklyScheduleReturnWeeklySchedule() {
        WeeklySchedule existedWeeklySchedule = new WeeklySchedule.Builder().setId("1").build();
        Mockito.when(weeklyScheduleRepo.getInstances())
                .thenReturn(Collections.singleton(existedWeeklySchedule));

        assertThat(informationService.checkFlightWeeklySchedule("1").get()).isEqualTo(existedWeeklySchedule);
    }

    @Test
    public void whenFlightNumberIsActualButNoSheduleinDBCheckFlightCustomScheduleThrowIllegalArgumentException() {
        CustomSchedule existedCustSched = new CustomSchedule.Builder().setId("2").build();
        Mockito.when(customScheduleRepo.getInstances())
                .thenReturn(Collections.singleton(existedCustSched));

        Assertions.assertThrows(ScheduleNotExistException.class, () ->
                informationService.checkFlightCustomSchedule("1"));
    }

    @Test
    public void whenFlightNumberIsActualThenCheckFlightCustomScheduleReturnWeeklySchedule() {
        CustomSchedule existedCustSched = new CustomSchedule.Builder().setId("1").build();
        Mockito.when(customScheduleRepo.getInstances())
                .thenReturn(Collections.singleton(existedCustSched));

        assertThat(informationService.checkFlightCustomSchedule("1").get()).isEqualTo(existedCustSched);
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckAvailableSeatsReturnSeatsList() {
        FlightSeat flightSeat = new FlightSeat.Builder().setReservationNumber("1").build();
        List<FlightSeat> flightSeatList = new ArrayList<>();
        flightSeatList.add(flightSeat);
        FlightInstance flightInstance = new FlightInstance.Builder().setSeats(flightSeatList).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkAvailableSeats(flightInstance).get()).isEqualTo(flightSeatList);
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckDepartureTimeReturnFlight() {
        FlightInstance flightInstance = new FlightInstance.Builder().setDepartureTime(time).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkDepartureTime(flightInstance).get()).isEqualTo(time);
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckArrivalTimeReturnFlight() {
        FlightInstance flightInstance = new FlightInstance.Builder().setArrivalTime(time).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkArrivalTime(flightInstance).get()).isEqualTo(time);
    }

    @Test
    public void whenDateIsActualThenSearchFlightByDateReturnListFlight() {
        Date date = new Date();
        Flight flight = new Flight.Builder().setDate(date).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances())
                .thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByDate(date).get()).isEqualTo(flights);
    }

    @Test
    public void whenDepartureAirportIsActualThenSearchFlightByDepartureAirportReturnListFlight() {
        Flight flight = new Flight.Builder().setArrival(airport).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances())
                .thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByDepartureAirport(airport).get()).isEqualTo(flights);
    }

    @Test
    public void whenArrivalAirportIsActualThenSearchFlightByArrivalAirportReturnListFlight() {
        Flight flight = new Flight.Builder().setDeparture(airport).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances()).thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByArrivalAirport(airport).get()).isEqualTo(flights);
    }
}