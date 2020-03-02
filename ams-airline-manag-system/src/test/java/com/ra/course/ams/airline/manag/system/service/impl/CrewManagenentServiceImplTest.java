package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CrewManagenentServiceImplTest {

        @Mock
        private Repository<Crew, String> crewRepository;

        private CrewManagenentServiceImpl crewManagenentService;

        @BeforeEach
        public void setup(){
                MockitoAnnotations.initMocks(this);
                crewManagenentService = new CrewManagenentServiceImpl();
                crewManagenentService.setCrewRepository(crewRepository);
        }

        @Test
        public void testThatFindByEmailReturnsCrew(){
                when(crewRepository.getInstances()).thenReturn(getCrew());
                Crew crew = crewManagenentService.findByEmail("ivanov@example.com");

                assertThat(crew).isNotNull();
                assertThat(crew.getName()).isEqualTo("Ivanov Ivan");
                assertThat(crew.getEmail()).isEqualTo("ivanov@example.com");

                verify(crewRepository, times(1)).getInstances();
        }

        @Test
        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument(){
                try {
                        crewManagenentService.findByEmail("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(crewRepository);
        }

        @Test
        public void testThatFindByEmailThrowsNoSuchElementExceptionWhenCallingWhenCannotFindPersonWithEmail(){
                when(crewRepository.getInstances()).thenReturn(getCrew());

                try {
                        crewManagenentService.findByEmail("unknown@example.com");
                        fail("Expected NoSuchElementException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NoSuchElementException.class);
                }

                verify(crewRepository, times(1)).getInstances();
        }

        @Test
        public void testThatFindByPhoneReturnsCrew(){
                Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(crewRepository.getInstance(any(String.class))).thenReturn(crewGiven);

                Crew crew = crewManagenentService.findByPhoneNumber("11111");
                assertThat(crew).isEqualToComparingFieldByField(crewGiven);
                verify(crewRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsCrewThrowsInstanceNotExistExceptionWhenNoSuchPersonAvalialable(){
                when(crewRepository.getInstance(any(String.class))).thenReturn(null);

                try {
                        crewManagenentService.findByPhoneNumber("11111");
                        fail("Expected InstanceNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(InstanceNotExistException.class);
                }

                verify(crewRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsCrewThrowsIllegalArgumentExceptionWhenCallWithEmptyArg(){
                try {
                        crewManagenentService.findByPhoneNumber("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(crewRepository);
        }

        @Test
        public void testThatAddInstanceReturnsCrew(){
                Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(crewRepository.addInstance(any())).thenReturn(crewGiven);

                Person person = crewManagenentService.add(crewGiven);
                assertThat(person).isEqualToComparingFieldByField(crewGiven);
                verify(crewRepository, times(1)).addInstance(any());
        }

        @Test
        public void testThatAddInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        crewManagenentService.add(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(crewRepository);
        }

        @Test
        public void testThatUpdateInstanceWithoutExceptions(){
                Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();

                try {
                        crewManagenentService.updateData(crewGiven);
                } catch (Exception e) {
                        fail("Unexpected exception thrown");
                }

                verify(crewRepository, times(1)).updateInstance(any());
        }

        @Test
        public void testThatUpdateInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        crewManagenentService.updateData(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(crewRepository);
        }

        @Test
        public void testThatRemoveInstanceProcessWithoutExceptions(){
                Crew crewGiven = new Crew.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();

                try {
                        crewManagenentService.remove(crewGiven);
                } catch (Exception e) {
                        fail("Unexpected exception thrown");
                }

                verify(crewRepository, times(1)).removeInstance(any());
        }

        @Test
        public void testThatRemoveInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        crewManagenentService.remove(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(crewRepository);
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
}
