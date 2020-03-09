package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CrewManagementServiceImplTest {

    @Mock
    private Repository<Crew, String> crewRepository;

    private CrewManagementServiceImpl crewManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        crewManagementService = new CrewManagementServiceImpl();
        crewManagementService.setCrewRepository(crewRepository);
    }

    @Test
    public void testThatAddFlightInstanceWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        FlightInstance flightInstanceToAdd = new FlightInstance();
        Crew updatedCrew = crewManagementService.addFlightInstance(crew, flightInstanceToAdd);

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
            crewManagementService.addFlightInstance(null, null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

    @Test
    public void whenAddFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                crewManagementService.addFlightInstance(new Crew(), null));
    }

    @Test
    public void testThatAddFlightInstanceThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            FlightInstance flightInstanceToAdd = new FlightInstance();
            crewManagementService.addFlightInstance(crew, flightInstanceToAdd);
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
        Crew updatedCrew = crewManagementService.removeFlightInstance(crew, flightInstanceToRemove);

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
            crewManagementService.removeFlightInstance(null, null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
        verifyZeroInteractions(crewRepository);
    }

        @Test
        public void whenRemoveFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
                Assertions.assertThrows(IllegalArgumentException.class, () ->
                        crewManagementService.removeFlightInstance(new Crew(), null));
        }

    @Test
    public void testThatRemoveFlightInstanceThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            FlightInstance flightInstanceToRemove = new FlightInstance();
            crewManagementService.addFlightInstance(crew, flightInstanceToRemove);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
        verify(crewRepository, times(1)).getInstance(any());
        verifyNoMoreInteractions(crewRepository);
    }

}
