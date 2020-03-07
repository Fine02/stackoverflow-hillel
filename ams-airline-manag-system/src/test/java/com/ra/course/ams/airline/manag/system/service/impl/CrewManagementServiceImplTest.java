package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
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

public class CrewManagementServiceImplTest {

    @Mock
    private Repository<Crew, String> crewRepository;

    private CrewManagementServiceImpl crewManagenentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        crewManagenentService = new CrewManagementServiceImpl();
        crewManagenentService.setCrewRepository(crewRepository);
    }

    @Test
    public void testThatAddFlightInstanceWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        FlightInstance flightInstanceToAdd = new FlightInstance();
        Crew updatedCrew = crewManagenentService.addFlightInstance(crew, flightInstanceToAdd);

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getFlightInstances()).hasSize(1);
        assertThat(updatedCrew.getFlightInstances().get(0)).isEqualTo(flightInstanceToAdd);

        verify(crewRepository, times(1)).updateInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatAddFlightInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.addFlightInstance(null, null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void whenAddFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                crewManagenentService.addFlightInstance(new Crew(), null));
    }

    @Test
    public void testThatAddFlightInstanceThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            FlightInstance flightInstanceToAdd = new FlightInstance();
            crewManagenentService.addFlightInstance(crew, flightInstanceToAdd);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatRemoveFlightInstanceWithoutExceptions() {
        FlightInstance flightInstanceToRemove = new FlightInstance();
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan")
                .setEmail("ivanov@example.com").setPhone("11111").addFlightInstance(flightInstanceToRemove).build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewManagenentService.removeFlightInstance(crew, flightInstanceToRemove);

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getFlightInstances()).isEmpty();
        assertThat(crewInRepo.getFlightInstances()).isEmpty();

        verify(crewRepository, times(1)).updateInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatRemoveFlightInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.removeFlightInstance(null, null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

        @Test
        public void whenRemoveFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
                Assertions.assertThrows(IllegalArgumentException.class, () ->
                        crewManagenentService.removeFlightInstance(new Crew(), null));
        }

    @Test
    public void testThatRemoveFlightInstanceThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            FlightInstance flightInstanceToRemove = new FlightInstance();
            crewManagenentService.addFlightInstance(crew, flightInstanceToRemove);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }


    @Test
    public void testThatFindByEmailReturnsCrew() {
        when(crewRepository.getInstances()).thenReturn(getCrew());
        Crew crew = crewManagenentService.findByEmail("ivanov@example.com");

        assertThat(crew).isNotNull();
        assertThat(crew.getName()).isEqualTo("Ivanov Ivan");
        assertThat(crew.getEmail()).isEqualTo("ivanov@example.com");

        verify(crewRepository, times(1)).getInstances();
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument() {
        try {
            crewManagenentService.findByEmail("");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }
    @Test
    public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithNullArgument() {
        try {
            crewManagenentService.findByEmail(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }


    @Test
    public void testThatFindByEmailThrowsCrewNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail() {
        when(crewRepository.getInstances()).thenReturn(getCrew());

        try {
            crewManagenentService.findByEmail("unknown@example.com");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstances();
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatFindByPhoneReturnsCrew() {
        Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any(String.class))).thenReturn(crewGiven);

        Crew crew = crewManagenentService.findByPhoneNumber("11111");
        assertThat(crew).isEqualToComparingFieldByField(crewGiven);
        verify(crewRepository, times(1)).getInstance("11111");
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsCrewNotExistExceptionWhenNoSuchPersonAvalialable() {
        when(crewRepository.getInstance(any(String.class))).thenReturn(null);

        try {
            crewManagenentService.findByPhoneNumber("11111");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }

        verify(crewRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsIllegalArgumentExceptionWhenCallWithEmptyArg() {
        try {
            crewManagenentService.findByPhoneNumber("");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsIllegalArgumentExceptionWhenCallWithNullArg() {
        try {
            crewManagenentService.findByPhoneNumber(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatAddInstanceReturnsCrew() {
        when(crewRepository.getInstance(any())).thenReturn(null);

        Crew crewToAdd = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew crew = crewManagenentService.add(crewToAdd);

        assertThat(crew).isEqualToComparingFieldByField(crewToAdd);
        verify(crewRepository, times(1)).getInstance(any());
        verify(crewRepository, times(1)).addInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatAddInstanceThrowsCrewAlreadyExistExceptionWhenTryToAddExistingCrew() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crewToAdd = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        try {
            crewManagenentService.add(crewToAdd);
            fail("Expected CrewAlreadyExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewAlreadyExistException.class);
        }

        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatAddInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.add(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatUpdatePhoneNumberWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewManagenentService.updatePhone(crew, "55285");

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getPhone()).isEqualTo("55285");

        verify(crewRepository, times(1)).updateInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatUpdatePhoneNumberThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.updatePhone(null, "55285");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatUpdatePhoneNumberThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewManagenentService.updatePhone(crew, "55285");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatUpdateEmailWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewManagenentService.updateEmail(crew, "ivanov@test.com");

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getEmail()).isEqualTo("ivanov@test.com");

        verify(crewRepository, times(1)).updateInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatUpdateEmailThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.updateEmail(null, "ivanov@test.com");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatUpdateEmailThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewManagenentService.updateEmail(crew, "ivanov@test.com");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatRemoveCrewWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crewToRemove = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        crewManagenentService.remove(crewToRemove);

        verify(crewRepository, times(1)).removeInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    @Test
    public void testThatRemoveInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewManagenentService.remove(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void testThatRemoveInstanceThrowCrewNotExistExceptionWhenCallWithNullValueArgument() {
        when(crewRepository.getInstance(any())).thenReturn(null);

        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewManagenentService.remove(crew);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

    private static Collection<Crew> getCrew() {
        Crew[] crews = {
                new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build(),
                new Crew.Builder().setName("Petrov Petro").setEmail("petrov@example.com").setPhone("22222").build(),
                new Crew.Builder().setName("Sidorov Sidor").setEmail("sidorov@example.com").setPhone("33333").build(),
                new Crew.Builder().setName("Egorov Egor").setEmail("egorov@example.com").setPhone("4444").build()
        };
        return Arrays.asList(crews);
    }

    @Test
    public void whenUpdateAddressWithCrewNullThenThrowIllegalArgumentException() {
        Crew crew = null;
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                crewManagenentService.updateAddress(crew, new Address()));
    }
}
