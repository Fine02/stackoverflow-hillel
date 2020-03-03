package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exceptions.AdminNotExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PersonAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PersonNotExistException;
import com.ra.course.ams.airline.manag.system.exceptions.PilotNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class PersonManagementServiceImpl implements PersonManagementService<Person> {

    private Repository<Person, String> personRepository;


    @Override
    public Person findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Person> persons = personRepository.getInstances();
        Person findedPerson = persons.stream()
                .filter(person -> email.equals(person.getEmail()))
                .findAny()
                .orElseThrow(() -> new PersonNotExistException("No person found for given email"));
        return new Person(findedPerson);
    }

    @Override
    public Person findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Person personFromRepo = personRepository.getInstance(phone);
        if (personFromRepo == null) {
            throw new PersonNotExistException("No person found for given phone number");
        }
        return new Person(personFromRepo);
    }

    @Override
    public Person add(Person person) {
        if (person == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        Person personFromRepo = personRepository.getInstance(person.getPhone());
        if (personFromRepo != null) {
            throw new PersonAlreadyExistException();
        }
        personFromRepo = new Person(person);
        personRepository.addInstance(personFromRepo);
        return person;
    }

    @Override
    public Person updatePhone(Person person, String phone) {
        if (person == null) {
            throw new NullPointerException("Cannot process updatePhone operation for null value argument.");
        }
        Person personRepositoryInstance = personRepository.getInstance(person.getPhone());
        if (personRepositoryInstance == null) {
            throw new PersonNotExistException();
        }
        personRepositoryInstance.setPhone(phone);
        personRepository.updateInstance(personRepositoryInstance);
        person.setPhone(phone);
        return person;
    }

    @Override
    public Person updateEmail(Person person, String email) {
        if (person == null) {
            throw new NullPointerException("Cannot process updateEmail operation for null value argument.");
        }
        Person personRepositoryInstance = personRepository.getInstance(person.getPhone());
        if (personRepositoryInstance == null) {
            throw new PersonNotExistException();
        }
        personRepositoryInstance.setEmail(email);
        personRepository.updateInstance(personRepositoryInstance);
        person.setEmail(email);
        return person;
    }

    @Override
    public Person updateAddress(Person person, Address address) {
        if (person == null) {
            throw new NullPointerException("Cannot process updateAddress operation for null value argument.");
        }
        Person personRepositoryInstance = personRepository.getInstance(person.getPhone());
        if (personRepositoryInstance == null) {
            throw new PersonNotExistException();
        }
        personRepositoryInstance.setAddress(address);
        personRepository.updateInstance(personRepositoryInstance);
        person.setAddress(address);
        return person;
    }

    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        Person personRepositoryInstance = personRepository.getInstance(person.getPhone());
        if (personRepositoryInstance == null) {
            throw new PersonNotExistException();
        }
        personRepository.removeInstance(personRepositoryInstance);
    }

    public Repository<Person, String> getPersonRepository() {
        return personRepository;
    }

    public void setPersonRepository(Repository<Person, String> personRepository) {
        this.personRepository = personRepository;
    }
}
