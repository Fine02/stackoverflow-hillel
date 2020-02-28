package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.UserManagementService;

import java.util.Collection;

public class UserManagenentServiceImpl implements UserManagementService<Person> {

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
    public void updateData(Person person) {
        if (person == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        personRepository.updateInstance(person);
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
