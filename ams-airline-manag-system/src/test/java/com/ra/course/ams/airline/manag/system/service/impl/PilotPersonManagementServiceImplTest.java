package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.PilotAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PilotsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PilotPersonManagementServiceImplTest {

    @Mock
    private PilotsRepository pilotRepository;

    private PilotPersonManagementServiceImpl pilotPersonManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        pilotPersonManagementService = new PilotPersonManagementServiceImpl();
        pilotPersonManagementService.setPilotRepo(pilotRepository);
    }

    @Test
    public void testThatFindByEmailReturnsPilot() {
        when(pilotRepository.getInstances()).thenReturn(getPilot());
        Pilot pilot = pilotPersonManagementService.findByEmail("ivanov@example.com").get();

        assertThat(pilot).isNotNull();
        assertThat(pilot.getName()).isEqualTo("Ivanov Ivan");
        assertThat(pilot.getEmail()).isEqualTo("ivanov@example.com");
    }

    @Test
    public void testThatFindByEmailThrowsPilotNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail() {
        when(pilotRepository.getInstances()).thenReturn(getPilot());

        try {
            pilotPersonManagementService.findByEmail("unknown@example.com");
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }

    @Test
    public void testThatFindByPhoneReturnsPilot() {
        Pilot pilotGiven = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(pilotRepository.getInstance(any(String.class))).thenReturn(pilotGiven);

        Pilot pilot = pilotPersonManagementService.findByPhoneNumber("11111").get();
        assertThat(pilot).isEqualToComparingFieldByField(pilotGiven);
    }

    @Test
    public void testThatFindByPhoneReturnsPilotThrowsPilotNotExistExceptionWhenNoSuchPersonAvalialable() {
        when(pilotRepository.getInstance(any(String.class))).thenReturn(null);

        try {
            pilotPersonManagementService.findByPhoneNumber("11111");
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }

    @Test
    public void testThatAddInstanceReturnsPilot() {
        when(pilotRepository.getInstance(any())).thenReturn(null);

        Pilot pilotToAdd = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Pilot pilot = pilotPersonManagementService.add(pilotToAdd).get();

        assertThat(pilot).isEqualToComparingFieldByField(pilotToAdd);
        verify(pilotRepository, times(1)).getInstance(any());
        verify(pilotRepository, times(1)).addInstance(any());
    }

    @Test
    public void testThatAddInstanceThrowsPilotAlreadyExistExceptionWhenTryToAddExistingPilot() {
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilotToAdd = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        try {
            pilotPersonManagementService.add(pilotToAdd);
            fail("Expected PilotAlreadyExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotAlreadyExistException.class);
        }

        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatUpdatePhoneNumberWithoutExceptions() {
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Pilot updatedPilot = pilotPersonManagementService.updatePhone(pilot, "55285").get();

        assertThat(updatedPilot).isEqualTo(pilot);
        assertThat(updatedPilot.getPhone()).isEqualTo("55285");

        verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatUpdatePhoneNumberThrowPilotNotExistExceptionIfNoSuchPilotFind() {
        when(pilotRepository.getInstance(any())).thenReturn(null);
        try {
            Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            pilotPersonManagementService.updatePhone(pilot, "55285");
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatUpdateEmailWithoutExceptions() {
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Pilot updatedPilot = pilotPersonManagementService.updateEmail(pilot, "ivanov@test.com").get();

        assertThat(updatedPilot).isEqualTo(pilot);
        assertThat(updatedPilot.getEmail()).isEqualTo("ivanov@test.com");

        verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatUpdateAdressWithoutExceptions() {
        Address testAddress = new Address.Builder("s", "c").build();
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setAddress(testAddress).setPhone("11111").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setAddress(testAddress).setPhone("11111").build();
        Pilot updatedPilot = pilotPersonManagementService.updateAddress(pilot, testAddress).get();

        assertThat(updatedPilot).isEqualTo(pilot);
        assertThat(updatedPilot.getAddress()).isEqualTo(testAddress);

        verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatUpdateEmailThrowPilotNotExistExceptionIfNoSuchPilotFind() {
        when(pilotRepository.getInstance(any())).thenReturn(null);
        try {
            Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            pilotPersonManagementService.updateEmail(pilot, "ivanov@test.com");
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }

    @Test
    public void testThatRemovePilotWithoutExceptions() {
        Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

        Pilot pilotToRemove = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        pilotPersonManagementService.remove(pilotToRemove);

        verify(pilotRepository, times(1)).removeInstance(eq(pilotInRepo));
        verify(pilotRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatRemoveInstanceThrowPilotNotExistExceptionWhenCallWithNullValueArgument() {
        when(pilotRepository.getInstance(any())).thenReturn(null);

        try {
            Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            pilotPersonManagementService.remove(pilot);
            fail("Expected PilotNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(PilotNotExistException.class);
        }
    }

    private static Collection<Pilot> getPilot() {
        Pilot[] pilots = {
                new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build(),
                new Pilot.Builder().setName("Petrov Petro").setEmail("petrov@example.com").setPhone("22222").build(),
                new Pilot.Builder().setName("Sidorov Sidor").setEmail("sidorov@example.com").setPhone("33333").build(),
                new Pilot.Builder().setName("Egorov Egor").setEmail("egorov@example.com").setPhone("4444").build()
        };
        return Arrays.asList(pilots);
    }
}
