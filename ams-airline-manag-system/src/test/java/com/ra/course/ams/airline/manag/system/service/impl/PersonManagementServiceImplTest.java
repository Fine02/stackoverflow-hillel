package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.PersonAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PersonNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PersonsRepository;
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

//public class PersonManagementServiceImplTest {
//
//        @Mock
//        private PersonsRepository personRepository;
//
//        private PersonManagementServiceImpl personManagenentService;
//
//        @BeforeEach
//        public void setup(){
//                MockitoAnnotations.initMocks(this);
//                personManagenentService = new PersonManagementServiceImpl();
//                personManagenentService.setPersonRepository(personRepository);
//        }
//
//        @Test
//        public void testThatFindByEmailReturnsPerson(){
//                when(personRepository.getInstances()).thenReturn(getPersons());
//                Person person = personManagenentService.findByEmail("ivanov@example.com");
//
//                assertThat(person).isNotNull();
//                assertThat(person.getName()).isEqualTo("Ivanov Ivan");
//                assertThat(person.getEmail()).isEqualTo("ivanov@example.com");
//        }
//
//        @Test
//        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithEmptyArgument(){
//                try {
//                        personManagenentService.findByEmail("");
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatFindByEmailThrowsIllegalArgumentExceptionWhenCallingWithNullArgument(){
//                try {
//                        personManagenentService.findByEmail(null);
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatFindByEmailThrowsPersonNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail(){
//                when(personRepository.getInstances()).thenReturn(getPersons());
//
//                try {
//                        personManagenentService.findByEmail("unknown@example.com");
//                        fail("Expected PersonNotExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonNotExistException.class);
//                }
//        }
//
//        @Test
//        public void testThatFindByPhoneReturnsPerson(){
//                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                when(personRepository.getInstance(any(String.class))).thenReturn(personGiven);
//
//                Person person = personManagenentService.findByPhoneNumber("11111");
//                assertThat(person).isEqualToComparingFieldByField(personGiven);
//        }
//
//        @Test
//        public void testThatFindByPhoneThrowsCrewNotExistExceptionWhenNoSuchPersonAvalialable(){
//                when(personRepository.getInstance(any(String.class))).thenReturn(null);
//
//                try {
//                        personManagenentService.findByPhoneNumber("11111");
//                        fail("Expected PersonNotExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonNotExistException.class);
//                }
//        }
//
//        @Test
//        public void testThatFindByPhoneThrowsIllegalArgumentExceptionWhenCallWithEmptyArg(){
//                try {
//                        personManagenentService.findByPhoneNumber("");
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatFindByPhoneThrowsIllegalArgumentExceptionWhenCallWithNullArg(){
//                try {
//                        personManagenentService.findByPhoneNumber(null);
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatAddInstanceReturnsPerson(){
//                when(personRepository.getInstance(any())).thenReturn(null);
//
//                Person personToAdd = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                Person person = personManagenentService.add(personToAdd);
//
//                assertThat(person).isEqualToComparingFieldByField(personToAdd);
//        }
//
//        @Test
//        public void testThatAddInstanceThrowsPersonAlreadyExistExceptionWhenTryToAddExistingPerson(){
//                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                when(personRepository.getInstance(any())).thenReturn(personInRepo);
//
//                Person personToAdd = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                try {
//                        personManagenentService.add(personToAdd);
//                        fail("Expected PersonAlreadyExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonAlreadyExistException.class);
//                }
//        }
//
//        @Test
//        public void testThatAddInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument(){
//                try {
//                        personManagenentService.add(null);
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatUpdatePhoneNumberWithoutExceptions(){
//                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                when(personRepository.getInstance(any())).thenReturn(personInRepo);
//
//                Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                Person updatedPerson = personManagenentService.updatePhone(person, "55285");
//
//                assertThat(updatedPerson).isEqualTo(person);
//                assertThat(updatedPerson.getPhone()).isEqualTo("55285");
//        }
//
//        @Test
//        public void testThatUpdatePhoneNumberThrowIllegalArgumentExceptionWhenCallWithNullValueArgument(){
//                try {
//                        personManagenentService.updatePhone(null, "55285");
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatUpdatePhoneNumberThrowPersonNotExistExceptionIfNoSuchPersonFind(){
//                when(personRepository.getInstance(any())).thenReturn(null);
//                try {
//                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                        personManagenentService.updatePhone(person, "55285");
//                        fail("Expected PersonNotExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonNotExistException.class);
//                }
//        }
//
//        @Test
//        public void testThatUpdateEmailWithoutExceptions(){
//                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                when(personRepository.getInstance(any())).thenReturn(personInRepo);
//
//                Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                Person updatedCrew = personManagenentService.updateEmail(person, "ivanov@test.com");
//
//                assertThat(updatedCrew).isEqualTo(person);
//                assertThat(updatedCrew.getEmail()).isEqualTo("ivanov@test.com");
//        }
//
//        @Test
//        public void testThatUpdateEmailThrowIllegalArgumentExceptionWhenCallWithNullValueArgument(){
//                try {
//                        personManagenentService.updateEmail(null, "ivanov@test.com");
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatUpdateEmailThrowPersonNotExistExceptionIfNoSuchPersonFind(){
//                when(personRepository.getInstance(any())).thenReturn(null);
//                try {
//                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                        personManagenentService.updateEmail(person, "ivanov@test.com");
//                        fail("Expected PersonNotExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonNotExistException.class);
//                }
//        }
//
//        @Test
//        public void testThatRemovePersonWithoutExceptions(){
//                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                when(personRepository.getInstance(any())).thenReturn(personInRepo);
//
//                Person personToRemove = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                personManagenentService.remove(personToRemove);
//        }
//
//        @Test
//        public void testThatRemoveInstanceThrowIllegalArgumentExceptionWhenCallWithNullValueArgument(){
//                try {
//                        personManagenentService.remove(null);
//                        fail("Expected IllegalArgumentException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(IllegalArgumentException.class);
//                }
//        }
//
//        @Test
//        public void testThatRemoveInstanceThrowPersonNotExistExceptionWhenCallWithNullValueArgument(){
//                when(personRepository.getInstance(any())).thenReturn(null);
//
//                try {
//                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
//                        personManagenentService.remove(person);
//                        fail("Expected PersonNotExistException to be thrown");
//                } catch (Exception e) {
//                        assertThat(e).isInstanceOf(PersonNotExistException.class);
//                }
//        }
//
//        @Test
//        public void whenUpdateAdressWithPersonIsNullThenThrowIllegalArgumentException() {
//                Assertions.assertThrows(IllegalArgumentException.class, () ->
//                        personManagenentService.updateAddress(null, new Address()));
//        }
//
//        private static Collection<Person> getPersons() {
//                Person[] persons = {
//                        new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build(),
//                        new Person.Builder().setName("Petrov Petro").setEmail("petrov@example.com").setPhone("22222").build(),
//                        new Person.Builder().setName("Sidorov Sidor").setEmail("sidorov@example.com").setPhone("33333").build(),
//                        new Person.Builder().setName("Egorov Egor").setEmail("egorov@example.com").setPhone("4444").build()
//                };
//                return Arrays.asList(persons);
//        }
//}
