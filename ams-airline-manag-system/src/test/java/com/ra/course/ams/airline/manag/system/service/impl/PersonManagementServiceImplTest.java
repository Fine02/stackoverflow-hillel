package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.PersonAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PersonNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PersonsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PersonManagementServiceImplTest {

        @Mock
        private PersonsRepository personRepository;

        private PersonManagementServiceImpl personManagenentService;

        @BeforeEach
        public void setup(){
                MockitoAnnotations.initMocks(this);
                personManagenentService = new PersonManagementServiceImpl();
                personManagenentService.setPersonRepository(personRepository);
        }

        @Test
        public void testThatFindByEmailReturnsPerson(){
                when(personRepository.getInstances()).thenReturn(getPersons());
                Person person = personManagenentService.findByEmail("ivanov@example.com").get();

                assertThat(person).isNotNull();
                assertThat(person.getName()).isEqualTo("Ivanov Ivan");
                assertThat(person.getEmail()).isEqualTo("ivanov@example.com");
        }

        @Test
        public void testThatFindByEmailThrowsPersonNotExistExceptionWhenCallingWhenCannotFindPersonWithEmail(){
                when(personRepository.getInstances()).thenReturn(getPersons());

                try {
                        personManagenentService.findByEmail("unknown@example.com");
                        fail("Expected PersonNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonNotExistException.class);
                }
        }

        @Test
        public void testThatFindByPhoneReturnsPerson(){
                Person personGiven = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any(String.class))).thenReturn(personGiven);

                Person person = personManagenentService.findByPhoneNumber("11111").get();
                assertThat(person).isEqualToComparingFieldByField(personGiven);
        }

        @Test
        public void testThatFindByPhoneThrowsCrewNotExistExceptionWhenNoSuchPersonAvalialable(){
                when(personRepository.getInstance(any(String.class))).thenReturn(null);

                try {
                        personManagenentService.findByPhoneNumber("11111");
                        fail("Expected PersonNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonNotExistException.class);
                }
        }

        @Test
        public void testThatAddInstanceReturnsPerson(){
                when(personRepository.getInstance(any())).thenReturn(null);

                Person personToAdd = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Person person = personManagenentService.add(personToAdd).get();

                assertThat(person).isEqualToComparingFieldByField(personToAdd);
        }

        @Test
        public void testThatAddInstanceThrowsPersonAlreadyExistExceptionWhenTryToAddExistingPerson(){
                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any())).thenReturn(personInRepo);

                Person personToAdd = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                try {
                        personManagenentService.add(personToAdd);
                        fail("Expected PersonAlreadyExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonAlreadyExistException.class);
                }
        }

        @Test
        public void testThatUpdatePhoneNumberWithoutExceptions(){
                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any())).thenReturn(personInRepo);

                Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Person updatedPerson = personManagenentService.updatePhone(person, "55285").get();

                assertThat(updatedPerson).isEqualTo(person);
                assertThat(updatedPerson.getPhone()).isEqualTo("55285");
        }

        @Test
        public void testThatUpdatePhoneNumberThrowPersonNotExistExceptionIfNoSuchPersonFind(){
                when(personRepository.getInstance(any())).thenReturn(null);
                try {
                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        personManagenentService.updatePhone(person, "55285");
                        fail("Expected PersonNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonNotExistException.class);
                }
        }

        @Test
        public void testThatUpdateEmailWithoutExceptions(){
                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any())).thenReturn(personInRepo);

                Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                Person updatedCrew = personManagenentService.updateEmail(person, "ivanov@test.com").get();

                assertThat(updatedCrew).isEqualTo(person);
                assertThat(updatedCrew.getEmail()).isEqualTo("ivanov@test.com");
        }

        @Test
        public void testThatUpdateAddressWithoutExceptions(){
                Address testAddress = new Address.Builder("s", "c").build();
                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setAddress(testAddress).setPhone("11111").build();
                when(personRepository.getInstance(any())).thenReturn(personInRepo);

                Person person = new Person.Builder().setName("Ivanov Ivan").setAddress(testAddress).setPhone("11111").build();
                Person updatedCrew = personManagenentService.updateAddress(person, testAddress).get();

                assertThat(updatedCrew).isEqualTo(person);
                assertThat(updatedCrew.getAddress()).isEqualTo(testAddress);
        }

        @Test
        public void testThatUpdateEmailThrowPersonNotExistExceptionIfNoSuchPersonFind(){
                when(personRepository.getInstance(any())).thenReturn(null);
                try {
                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        personManagenentService.updateEmail(person, "ivanov@test.com");
                        fail("Expected PersonNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonNotExistException.class);
                }
        }

        @Test
        public void testThatRemovePersonWithoutExceptions(){
                Person personInRepo = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                when(personRepository.getInstance(any())).thenReturn(personInRepo);

                Person personToRemove = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                personManagenentService.remove(personToRemove);
        }

        @Test
        public void testThatRemoveInstanceThrowPersonNotExistExceptionWhenCallWithNullValueArgument(){
                when(personRepository.getInstance(any())).thenReturn(null);

                try {
                        Person person = new Person.Builder().setName("Ivanov Ivan").setEmail("ivanov@example.com").setPhone("11111").build();
                        personManagenentService.remove(person);
                        fail("Expected PersonNotExistException to be thrown");
                } catch (Exception e) {
                        assertThat(e).isInstanceOf(PersonNotExistException.class);
                }
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
