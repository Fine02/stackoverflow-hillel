package com.ra.course.ams.airline.manag.system;

import com.ra.course.ams.airline.manag.system.repository.flight.*;
import com.ra.course.ams.airline.manag.system.repository.person.*;
import com.ra.course.ams.airline.manag.system.service.impl.*;
import com.ra.course.ams.airline.manag.system.service.impl.notification.EmailNotificationService;
import com.ra.course.ams.airline.manag.system.service.impl.notification.PostalNotificationService;
import com.ra.course.ams.airline.manag.system.service.impl.notification.SmsNotificationService;
import com.ra.course.ams.airline.manag.system.service.impl.payment.CashTransactionService;
import com.ra.course.ams.airline.manag.system.service.impl.payment.CheckTransactionService;
import com.ra.course.ams.airline.manag.system.service.impl.payment.CreditCardTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest (classes = AirlineSpringApplication.class)
public class SpringBootContextAirlineTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private AircraftRepository aircraftRepository;

    @MockBean
    private AccountsRepository accountRepository;

    @MockBean
    private AdminsRepository adminRepository;

    @MockBean
    private CrewRepository crewRepository;

    @MockBean
    private FlightInstanceRepository flightInstRepo;

    @MockBean
    private  WeeklyScheduleRepository weeklySchedRepo;

    @MockBean
    private CustomScheduleRepository customSchedRepo;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private PersonsRepository personRepository;

    @MockBean
    private PilotsRepository pilotRepo;

    @Test
    void checkAccountManagementServiceContextIsNotNull() {
        assertNotNull(context.getBean(AccountManagementServiceImpl.class));
    }

    @Test
    void checkAdminManagementServiceContextIsNotNull() {
        assertNotNull(context.getBean(AdminManagementServiceImpl.class));
    }

    @Test
    void checkAircraftManagementServiceContextIsNotNull() {
        assertNotNull(context.getBean(AircraftManagementServiceImpl.class));
    }

    @Test
    void checkCrewManagementServiceContextIsNotNull() {
        assertNotNull(context.getBean(CrewManagementServiceImpl.class));
    }

    @Test
    void checkCrewPersonServiceContextIsNotNull() {
        assertNotNull(context.getBean(CrewPersonManagementServiceImpl.class));
    }

    @Test
    void checkFlightInstServiceContextIsNotNull() {
        assertNotNull(context.getBean(FlightInstSerImpl.class));
    }

    @Test
    void checkFlightScheduleServiceContextIsNotNull() {
        assertNotNull(context.getBean(FlightScheduleServiceImpl.class));
    }

    @Test
    void checkFlightServiceContextIsNotNull() {
        assertNotNull(context.getBean(FlightServiceImpl.class));
    }

    @Test
    void checkInformationServiceContextIsNotNull() {
        assertNotNull(context.getBean(InformationServiceImpl.class));
    }

    @Test
    void checkPersonManagemenServiceContextIsNotNull() {
        assertNotNull(context.getBean(PersonManagementServiceImpl.class));
    }

    @Test
    void checkPilotManagemenServiceContextIsNotNull() {
        assertNotNull(context.getBean(PilotManagementServiceImpl.class));
    }

    @Test
    void checkPilotPersonManagemenServiceContextIsNotNull() {
        assertNotNull(context.getBean(PilotPersonManagementServiceImpl.class));
    }

    @Test
    void checkNotificationsContextIsNotNull() {
        assertNotNull(context.getBean(EmailNotificationService.class));
        assertNotNull(context.getBean(PostalNotificationService.class));
        assertNotNull(context.getBean(SmsNotificationService.class));
    }

    @Test
    void checkPaymentContextIsNotNull() {
        assertNotNull(context.getBean(CashTransactionService.class));
        assertNotNull(context.getBean(CheckTransactionService.class));
        assertNotNull(context.getBean(CreditCardTransactionService.class));
    }
}