package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exceptions.PilotAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PilotManagementServiceImplTest {

        @Mock
        private Repository<Pilot, String> pilotRepository;

        private PilotManagementServiceImpl pilotManagenentService;

        @BeforeEach
        public void setup(){
                MockitoAnnotations.initMocks(this);
                pilotManagenentService = new PilotManagementServiceImpl();
                pilotManagenentService.setPilotRepository(pilotRepository);
        }

        @Test
        public void testThatAddFlightInstanceWithoutExceptions(){
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                FlightInstance flightInstanceToAdd = new FlightInstance();
                Pilot updatedPilot = pilotManagenentService.addFlightInstance(pilot, flightInstanceToAdd);

                assertThat(updatedPilot).isEqualTo(pilot);
                assertThat(updatedPilot.getFlightInstances()).hasSize(1);
                assertThat(updatedPilot.getFlightInstances().get(0)).isEqualTo(flightInstanceToAdd);

                verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatAddFlightInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.addFlightInstance(null, null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatAddFlightInstanceThrowPilotNotExistExceptionIfNoSuchPilotFind(){
                when(pilotRepository.getInstance(any())).thenReturn(null);
                try {
                        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        FlightInstance flightInstanceToAdd = new FlightInstance();
                        pilotManagenentService.addFlightInstance(pilot, flightInstanceToAdd);
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatRemoveFlightInstanceWithoutExceptions(){
                FlightInstance flightInstanceToRemove = new FlightInstance();
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan")
                        .setEmail("ivanov@example.com").setPhone("11111").addFlightInstance(flightInstanceToRemove).build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Pilot updatedPilot = pilotManagenentService.removeFlightInstance(pilot, flightInstanceToRemove);

                assertThat(updatedPilot).isEqualTo(pilot);
                assertThat(updatedPilot.getFlightInstances()).isEmpty();
                assertThat(pilotInRepo.getFlightInstances()).isEmpty();

                verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatRemoveFlightInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.removeFlightInstance(null, null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatRemoveFlightInstanceThrowPilotNotExistExceptionIfNoSuchPilotFind(){
                when(pilotRepository.getInstance(any())).thenReturn(null);
                try {
                        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        FlightInstance flightInstanceToRemove = new FlightInstance();
                        pilotManagenentService.addFlightInstance(pilot, flightInstanceToRemove);
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }


        @Test
        public void testThatFindByEmailReturnsPilot(){
                when(pilotRepository.getInstances()).thenReturn(getPilot());
                Pilot pilot = pilotManagenentService.findByEmail("ivanov@example.com");

                assertThat(pilot).isNotNull();
                assertThat(pilot.getName()).isEqualTo("Ivanov Ivan");
                assertThat(pilot.getEmail()).isEqualTo("ivanov@example.com");

                verify(pilotRepository, times(1)).getInstances();
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument(){
                try {
                        pilotManagenentService.findByEmail("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatFindByEmailThrowsPilotNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail(){
                when(pilotRepository.getInstances()).thenReturn(getPilot());

                try {
                        pilotManagenentService.findByEmail("unknown@example.com");
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstances();
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatFindByPhoneReturnsPilot(){
                Pilot pilotGiven = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any(String.class))).thenReturn(pilotGiven);

                Pilot pilot = pilotManagenentService.findByPhoneNumber("11111");
                assertThat(pilot).isEqualToComparingFieldByField(pilotGiven);
                verify(pilotRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsPilotThrowsPilotNotExistExceptionWhenNoSuchPersonAvalialable(){
                when(pilotRepository.getInstance(any(String.class))).thenReturn(null);

                try {
                        pilotManagenentService.findByPhoneNumber("11111");
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }

                verify(pilotRepository, times(1)).getInstance(any());
        }

        @Test
        public void testThatFindByPhoneReturnsPilotThrowsIllegalArgumentExceptionWhenCallWithEmptyArg(){
                try {
                        pilotManagenentService.findByPhoneNumber("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatAddInstanceReturnsPilot(){
                when(pilotRepository.getInstance(any())).thenReturn(null);

                Pilot pilotToAdd = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Pilot pilot = pilotManagenentService.add(pilotToAdd);

                assertThat(pilot).isEqualToComparingFieldByField(pilotToAdd);
                verify(pilotRepository, times(1)).getInstance(any());
                verify(pilotRepository, times(1)).addInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatAddInstanceThrowsPilotAlreadyExistExceptionWhenTryToAddExistingPilot(){
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilotToAdd = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                try {
                        pilotManagenentService.add(pilotToAdd);
                        fail("Expected PilotAlreadyExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotAlreadyExistException.class);
                }

                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatAddInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.add(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberWithoutExceptions(){
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Pilot updatedPilot = pilotManagenentService.updatePhone(pilot, "55285");

                assertThat(updatedPilot).isEqualTo(pilot);
                assertThat(updatedPilot.getPhone()).isEqualTo("55285");

                verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.updatePhone(null, "55285");
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdatePhoneNumberThrowPilotNotExistExceptionIfNoSuchPilotFind(){
                when(pilotRepository.getInstance(any())).thenReturn(null);
                try {
                        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        pilotManagenentService.updatePhone(pilot, "55285");
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdateEmailWithoutExceptions(){
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Pilot updatedPilot = pilotManagenentService.updateEmail(pilot, "ivanov@test.com");

                assertThat(updatedPilot).isEqualTo(pilot);
                assertThat(updatedPilot.getEmail()).isEqualTo("ivanov@test.com");

                verify(pilotRepository, times(1)).updateInstance(eq(pilotInRepo));
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdateEmailThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.updateEmail(null, "ivanov@test.com");
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatUpdateEmailThrowPilotNotExistExceptionIfNoSuchPilotFind(){
                when(pilotRepository.getInstance(any())).thenReturn(null);
                try {
                        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        pilotManagenentService.updateEmail(pilot, "ivanov@test.com");
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatRemovePilotWithoutExceptions(){
                Pilot pilotInRepo = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(pilotRepository.getInstance(any())).thenReturn(pilotInRepo);

                Pilot pilotToRemove = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                pilotManagenentService.remove(pilotToRemove);

                verify(pilotRepository, times(1)).removeInstance(eq(pilotInRepo));
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
        }

        @Test
        public void testThatRemoveInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        pilotManagenentService.remove(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(pilotRepository);
        }

        @Test
        public void testThatRemoveInstanceThrowPilotNotExistExceptionWhenCallWithNullValueArgument(){
                when(pilotRepository.getInstance(any())).thenReturn(null);

                try {
                        Pilot pilot = new Pilot.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        pilotManagenentService.remove(pilot);
                        fail("Expected PilotNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PilotNotExistException.class);
                }
                verify(pilotRepository, times(1)).getInstance(any());
                verifyNoMoreInteractions(pilotRepository);
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
