package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.CrewRepository;
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
    private CrewRepository crewRepository;

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
        Crew updatedCrew = crewManagementService.addFlightInstance(crew, flightInstanceToAdd).get();

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getFlightInstances()).hasSize(1);
        assertThat(updatedCrew.getFlightInstances().get(0)).isEqualTo(flightInstanceToAdd);
    }

//    @Test
//    public void testThatAddFlightInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
//        try {
//            crewManagementService.addFlightInstance(null, null);
//            fail("Expected IllegalArgumentException to be thrown");
//        } catch (Exception e) {
//            assertThat(e).isInstanceOf(IllegalArgumentException.class);
//        }
//    }

//    @Test
//    public void whenAddFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
//
//        Assertions.assertThrows(IllegalArgumentException.class, () ->
//                crewManagementService.addFlightInstance(new Crew(), null));
//    }

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
    }

    @Test
    public void testThatRemoveFlightInstanceWithoutExceptions() {
        FlightInstance flightInstanceToRemove = new FlightInstance();
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan")
                .setEmail("ivanov@example.com").setPhone("11111").addFlightInstance(flightInstanceToRemove).build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewManagementService.removeFlightInstance(crew, flightInstanceToRemove).get();

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getFlightInstances()).isEmpty();
        assertThat(crewInRepo.getFlightInstances()).isEmpty();
    }

//    @Test
//    public void testThatRemoveFlightInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
//        try {
//            crewManagementService.removeFlightInstance(null, null);
//            fail("Expected IllegalArgumentException to be thrown");
//        } catch (Exception e) {
//            assertThat(e).isInstanceOf(IllegalArgumentException.class);
//        }
//    }

//        @Test
//        public void whenRemoveFlightInstanceWithFlightInstanceNullThenThrowIllegalArgumentException() {
//                Assertions.assertThrows(IllegalArgumentException.class, () ->
//                        crewManagementService.removeFlightInstance(new Crew(), null));
//        }

    @Test
    public void testThatRemoveFlightInstanceThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            FlightInstance flightInstanceToRemove = new FlightInstance();
            crewManagementService.removeFlightInstance(crew, flightInstanceToRemove);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
    }
}
