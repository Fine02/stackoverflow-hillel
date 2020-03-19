package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.exception.CrewAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.CrewNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.CrewRepository;
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

public class CrewPersonManagementServiceImplTest {

    @Mock
    private CrewRepository crewRepository;

    private CrewPersonManagementServiceImpl crewPersonManagementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        crewPersonManagementService = new CrewPersonManagementServiceImpl();
        crewPersonManagementService.setCrewRepository(crewRepository);
    }


    @Test
    public void testThatFindByEmailReturnsCrew() {
        when(crewRepository.getInstances()).thenReturn(getCrew());
        Crew crew = crewPersonManagementService.findByEmail("ivanov@example.com");

        assertThat(crew).isNotNull();
        assertThat(crew.getName()).isEqualTo("Ivanov Ivan");
        assertThat(crew.getEmail()).isEqualTo("ivanov@example.com");
    }

    @Test
    public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument() {
        try {
            crewPersonManagementService.findByEmail("");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithNullArgument() {
        try {
            crewPersonManagementService.findByEmail(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Test
    public void testThatFindByEmailThrowsCrewNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail() {
        when(crewRepository.getInstances()).thenReturn(getCrew());

        try {
            crewPersonManagementService.findByEmail("unknown@example.com");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
    }

    @Test
    public void testThatFindByPhoneReturnsCrew() {
        Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any(String.class))).thenReturn(crewGiven);

        Crew crew = crewPersonManagementService.findByPhoneNumber("11111");
        assertThat(crew).isEqualToComparingFieldByField(crewGiven);
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsCrewNotExistExceptionWhenNoSuchPersonAvalialable() {
        when(crewRepository.getInstance(any(String.class))).thenReturn(null);

        try {
            crewPersonManagementService.findByPhoneNumber("11111");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsIllegalArgumentExceptionWhenCallWithEmptyArg() {
        try {
            crewPersonManagementService.findByPhoneNumber("");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatFindByPhoneReturnsCrewThrowsIllegalArgumentExceptionWhenCallWithNullArg() {
        try {
            crewPersonManagementService.findByPhoneNumber(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatAddInstanceReturnsCrew() {
        when(crewRepository.getInstance(any())).thenReturn(null);

        Crew crewToAdd = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew crew = crewPersonManagementService.add(crewToAdd);

        assertThat(crew).isEqualToComparingFieldByField(crewToAdd);
    }

    @Test
    public void testThatAddInstanceThrowsCrewAlreadyExistExceptionWhenTryToAddExistingCrew() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crewToAdd = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        try {
            crewPersonManagementService.add(crewToAdd);
            fail("Expected CrewAlreadyExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewAlreadyExistException.class);
        }
    }

    @Test
    public void testThatAddInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewPersonManagementService.add(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatUpdatePhoneNumberWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewPersonManagementService.updatePhone(crew, "55285");

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getPhone()).isEqualTo("55285");
    }

    @Test
    public void testThatUpdatePhoneNumberThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewPersonManagementService.updatePhone(null, "55285");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatUpdatePhoneNumberThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewPersonManagementService.updatePhone(crew, "55285");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
    }

    @Test
    public void testThatUpdateEmailWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        Crew updatedCrew = crewPersonManagementService.updateEmail(crew, "ivanov@test.com");

        assertThat(updatedCrew).isEqualTo(crew);
        assertThat(updatedCrew.getEmail()).isEqualTo("ivanov@test.com");
    }

    @Test
    public void testThatUpdateEmailThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewPersonManagementService.updateEmail(null, "ivanov@test.com");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatUpdateEmailThrowCrewNotExistExceptionIfNoSuchCrewFind() {
        when(crewRepository.getInstance(any())).thenReturn(null);
        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewPersonManagementService.updateEmail(crew, "ivanov@test.com");
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
    }

    @Test
    public void testThatRemoveCrewWithoutExceptions() {
        Crew crewInRepo = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        when(crewRepository.getInstance(any())).thenReturn(crewInRepo);

        Crew crewToRemove = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
        crewPersonManagementService.remove(crewToRemove);

        verify(crewRepository, times(1)).removeInstance(eq(crewInRepo));
        verify(crewRepository, times(1)).getInstance(any());
    }

    @Test
    public void testThatRemoveInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument() {
        try {
            crewPersonManagementService.remove(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testThatRemoveInstanceThrowCrewNotExistExceptionWhenCallWithNullValueArgument() {
        when(crewRepository.getInstance(any())).thenReturn(null);

        try {
            Crew crew = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
            crewPersonManagementService.remove(crew);
            fail("Expected CrewNotExistException to be thrown");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CrewNotExistException.class);
        }
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
                crewPersonManagementService.updateAddress(crew, new Address()));
    }
}
