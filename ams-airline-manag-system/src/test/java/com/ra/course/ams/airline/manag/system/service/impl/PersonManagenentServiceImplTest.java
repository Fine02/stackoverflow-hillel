package com.ra.course.ams.airline.manag.system.service.impl;

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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonManagenentServiceImplTest {

        @Mock
        private Repository<Person, String> personRepository;

        private PersonManagenentServiceImpl personManagenentService;

        @BeforeEach
        public void setup(){
                MockitoAnnotations.initMocks(this);
                personManagenentService = new PersonManagenentServiceImpl();
                personManagenentService.setPersonRepository(personRepository);
        }

        @Test
        public void testThatFindByEmailReturnsPerson(){
                when(personRepository.getInstances()).thenReturn(getPersons());
                Person person = personManagenentService.findByEmail("ivanov@example.com");

                assertThat(person).isNotNull();
                assertThat(person.getName()).isEqualTo("Ivanov Ivan");
                assertThat(person.getEmail()).isEqualTo("ivanov@example.com");

                verify(personRepository, times(1)).getInstances();
        }

        @Test
        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument(){
                try {
                        personManagenentService.findByEmail("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(personRepository);
        }

        @Test
        public void testThatFindByEmailThrowsNoSuchElementExceptionWhenCallingWhenCannotFindPersonWithEmail(){
                when(personRepository.getInstances()).thenReturn(getPersons());

                try {
                        personManagenentService.findByEmail("unknown@example.com");
                        fail("Expected NoSuchElementException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NoSuchElementException.class);
                }

                verify(personRepository, times(1)).getInstances();
        }

        @Test
        public void testThatFindByPhoneReturnsPerson(){
                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any(String.class))).thenReturn(personGiven);

                Person person = personManagenentService.findByPhoneNumber("11111");
                assertThat(person).isEqualToComparingFieldByField(personGiven);
                verify(personRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsPersonThrowsInstanceNotExistExceptionWhenNoSuchPersonAvalialable(){
                when(personRepository.getInstance(any(String.class))).thenReturn(null);

                try {
                        personManagenentService.findByPhoneNumber("11111");
                        fail("Expected InstanceNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(InstanceNotExistException.class);
                }

                verify(personRepository, times(1)).getInstance("11111");
        }

        @Test
        public void testThatFindByPhoneReturnsPersonThrowsIllegalArgumentExceptionWhenCallWithEmptyArg(){
                try {
                        personManagenentService.findByPhoneNumber("");
                        fail("Expected IllegalArgumentException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
                }
                verifyZeroInteractions(personRepository);
        }

        @Test
        public void testThatAddInstanceReturnsPerson(){
                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.addInstance(any())).thenReturn(personGiven);

                Person person = personManagenentService.add(personGiven);
                assertThat(person).isEqualToComparingFieldByField(personGiven);
                verify(personRepository, times(1)).addInstance(any());
        }

        @Test
        public void testThatAddInstanceThrowNullPointerExceptionWhenCallWithNullalueArgument(){
                try {
                        personManagenentService.add(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(personRepository);
        }

        @Test
        public void testThatAddInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        personManagenentService.add(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(personRepository);
        }

//        @Test
//        public void testThatUpdateInstanceWithoutExceptions(){
//                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//
//                try {
//                        personManagenentService.updateData(personGiven);
//                } catch (Exception e) {
//                        fail("Unexpected exception thrown");
//                }
//
//                verify(personRepository, times(1)).updateInstance(any());
//        }

//        @Test
//        public void testThatUpdateInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
//                try {
//                        personManagenentService.updateData(null);
//                        fail("Expected NullPointerException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(NullPointerException.class);
//                }
//                verifyZeroInteractions(personRepository);
//        }

        @Test
        public void testThatRemoveInstanceProcessWithoutExceptions(){
                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();

                try {
                        personManagenentService.remove(personGiven);
                } catch (Exception e) {
                        fail("Unexpected exception thrown");
                }

                verify(personRepository, times(1)).removeInstance(any());
        }

        @Test
        public void testThatRemoveInstanceThrowNullPointerExceptionWhenCallWithNullValueArgument(){
                try {
                        personManagenentService.remove(null);
                        fail("Expected NullPointerException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(NullPointerException.class);
                }
                verifyZeroInteractions(personRepository);
        }

        private static Collection<Person> getPersons() {
                Person[] persons = {
                        new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build(),
                        new Person.Builder().setName("Petrov Petro").setEmail("petrov@example.com").setPhone("22222").build(),
                        new Person.Builder().setName("Sidorov Sidor").setEmail("sidorov@example.com").setPhone("33333").build(),
                        new Person.Builder().setName("Egorov Egor").setEmail("egorov@example.com").setPhone("4444").build()
                };
                return Arrays.asList(persons);
        }
}
