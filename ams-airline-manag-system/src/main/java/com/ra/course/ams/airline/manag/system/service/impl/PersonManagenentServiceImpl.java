package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class PersonManagenentServiceImpl implements PersonManagementService<Person> {

    private Repository<Person, String> personRepository;
    
    @Override
    public Person findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        Collection<Person> persons = personRepository.getInstances();
        return persons.stream()
                .filter(person -> email.equals(person.getEmail()))
                .findAny()
                .orElseThrow();
    }

    @Override
    public Person findByPhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        Person person = personRepository.getInstance(phone);
        if (person == null) {
            throw new InstanceNotExistException("No person found for given phone number");
        }
        return person;
    }

    @Override
    public Person add(Person person) {
        if (person == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return personRepository.addInstance(person);
    }

    @Override
    public Person updatePhone(Person person, String phone) {
        return null;
    }

    @Override
    public Person updateEmail(Person person, String email) {
        return null;
    }

    @Override
    public Person updateAddress(Person person, Address address) {
        return null;
    }


    @Override
    public void remove(Person person) {
        if (person == null) {
            throw new NullPointerException("Cannot process remove operation for null value argument.");
        }
        personRepository.removeInstance(person);
    }

    public Repository<Person, String> getPersonRepository() {
        return personRepository;
    }

    public void setPersonRepository(Repository<Person, String> personRepository) {
        this.personRepository = personRepository;
    }
}
