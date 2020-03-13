package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.repository.flight.WeeklyScheduleRepository;
import com.ra.course.ams.airline.manag.system.service.InformationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class InformationServiceImplTest {

    InformationServiceImpl informationService;

    @Mock
    private Repository<WeeklySchedule, String> weeklyScheduleRepo;
    private Repository<CustomSchedule, String> customScheduleRepo;
    private Repository<FlightInstance, String> flightInstRepo;
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
}
