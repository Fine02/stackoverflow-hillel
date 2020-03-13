package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.ReservationWasNotModifiedException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.impl.notification.EmailNotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class InformationServiceImplTest {

    private InformationServiceImpl informationServiceImpl;

    @Mock
    private Repository<WeeklySchedule, String> weeklyScheduleRepo;
    private Repository<Person, String> personRepository;
    private PersonManagementServiceImpl personManagenentService;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        informationServiceImpl = new InformationServiceImpl();
    }

    @Test
    public void whenFlightNumberIsNullCheckFlightWeeklyScheduleThrowIllegalArgumentException () {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationServiceImpl.checkFlightWeeklySchedule(null));
    }

    @Test
    public void whenFlightNumberIsEmptyCheckFlightWeeklyScheduleThrowIllegalArgumentException () {

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                informationServiceImpl.checkFlightWeeklySchedule(" "));
    }

    @Test
    public void whenFlightNumberIsActualThenCheckFlightWeeklyScheduleReturnWeeklySchedule () {
        WeeklySchedule existedWeeklySchedule = new WeeklySchedule.Builder().setId("1").build();
        Mockito.when(weeklyScheduleRepo.getInstances()).thenReturn(Collections.singleton(existedWeeklySchedule));

        assertThat(informationServiceImpl.checkFlightWeeklySchedule("1")).isEqualTo(existedWeeklySchedule);
    }
}
