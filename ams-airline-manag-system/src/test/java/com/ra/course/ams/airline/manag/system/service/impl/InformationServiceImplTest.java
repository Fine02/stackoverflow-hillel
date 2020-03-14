package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.exception.ScheduleNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
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

    @Mock
    private Repository<CustomSchedule, String> customScheduleRepo;
    @Mock
    private Repository<WeeklySchedule, String> weeklyScheduleRepo;
    @Mock
    private Repository<FlightInstance, String> flightInstRepo;
    @Mock
    private Repository<Flight, String> flightRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        informationService = new InformationServiceImpl(weeklyScheduleRepo, customScheduleRepo, flightInstRepo, flightRepository);
    }

    @Test
    public void whenFlightNumberIsNullCheckFlightWeeklyScheduleThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkFlightWeeklySchedule(null));
    }

    @Test
    public void whenFlightNumberIsEmptyCheckFlightWeeklyScheduleThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkFlightWeeklySchedule(" "));
    }

    @Test
    public void whenFlightNumberIsActualThenCheckFlightWeeklyScheduleReturnWeeklySchedule() {
        WeeklySchedule existedWeeklySchedule = new WeeklySchedule.Builder().setId("1").build();
        Mockito.when(weeklyScheduleRepo.getInstances())
                .thenReturn(Collections.singleton(existedWeeklySchedule));

        assertThat(informationService.checkFlightWeeklySchedule("1")).isEqualTo(existedWeeklySchedule);
    }

    @Test
    public void whenFlightNumberIsNullCheckFlightCustomScheduleThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkFlightCustomSchedule(null));
    }

    @Test
    public void whenFlightNumberIsEmptyCheckFlightCustomScheduleThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkFlightCustomSchedule(" "));
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

        assertThat(informationService.checkFlightCustomSchedule("1")).isEqualTo(existedCustSched);
    }

    @Test
    public void whenFlightInstanceIsNullThenCheckAvailableSeatsThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkAvailableSeats(null));
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckAvailableSeatsReturnSeatsList() {
        FlightSeat flightSeat = new FlightSeat.Builder().setReservationNumber("1").build();
        List <FlightSeat> flightSeatList = new ArrayList<FlightSeat>();
        flightSeatList.add(flightSeat);
        FlightInstance flightInstance = new FlightInstance.Builder().setSeats(flightSeatList).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkAvailableSeats(flightInstance)).isEqualTo(flightSeatList);
    }

    @Test
    public void whenFlightInstanceIsNullThenCheckDepartureTimeThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkDepartureTime(null));
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckDepartureTimeReturnFlight() {
        Time time = new Time(1);
        FlightInstance flightInstance = new FlightInstance.Builder().setDepartureTime(time).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkDepartureTime(flightInstance)).isEqualTo(time);
    }

    @Test
    public void whenFlightInstanceIsNullThenCheckArrivalTimeThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.checkArrivalTime(null));
    }

    @Test
    public void whenFlightInstanceIsActualThenCheckArrivalTimeReturnFlight() {
        Time time = new Time(1);
        FlightInstance flightInstance = new FlightInstance.Builder().setArrivalTime(time).build();
        Mockito.when(flightInstRepo.getInstances())
                .thenReturn(Collections.singleton(flightInstance));

        assertThat(informationService.checkArrivalTime(flightInstance)).isEqualTo(time);
    }

    @Test
    public void whenDateIsNullThenSearchFlightByDateThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.searchFlightByDate(null));
    }

    @Test
    public void whenDateIsActualThenSearchFlightByDateReturnListFlight() {
        Date date = new Date();
        Flight flight = new Flight.Builder().setDate(date).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances())
                .thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByDate(date)).isEqualTo(flights);
    }

    @Test
    public void whenAirportIsNullThenSearchFlightByDepartureAirportThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.searchFlightByDepartureAirport(null));
    }

    @Test
    public void whenDepartureAirportIsActualThenSearchFlightByDepartureAirportReturnListFlight() {
        Airport airport = new Airport();
        Flight flight = new Flight.Builder().setArrival(airport).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances())
                .thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByDepartureAirport(airport)).isEqualTo(flights);
    }

    @Test
    public void whenAirportIsNullThenSearchFlightByArrivalAirportThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationService.searchFlightByArrivalAirport(null));
    }

    @Test
    public void whenArrivalAirportIsActualThenSearchFlightByArrivalAirportReturnListFlight() {
        Airport airport = new Airport();
        Flight flight = new Flight.Builder().setDeparture(airport).build();
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Mockito.when(flightRepository.getInstances()).thenReturn(Collections.singleton(flight));

        assertThat(informationService.searchFlightByArrivalAirport(airport)).isEqualTo(flights);
    }
}
