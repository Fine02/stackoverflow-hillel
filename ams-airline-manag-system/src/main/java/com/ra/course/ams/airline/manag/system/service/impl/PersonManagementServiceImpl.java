package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.PersonAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PersonNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;

import java.util.Collection;

public class PersonManagementServiceImpl implements PersonManagementService<Person> {

    private Repository<Person, String> personRepository;

    @Override
    public Person findByEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address for search cannot be null, empty or blank");
        }
        final Collection<Person> persons = personRepository.getInstances();
        final Person findedPerson = persons.stream()
                .filter(person -> email.equals(person.getEmail()))
                .findAny()
                .orElseThrow(() -> new PersonNotExistException("No person found for given email"));
        return new Person(findedPerson);
    }

    @Override
    public Person findByPhoneNumber(final String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone for search cannot be null, empty or blank");
        }
        final Person personFromRepo = personRepository.getInstance(phone);
        if (personFromRepo == null) {
            throw new PersonNotExistException("No person found for given phone number");
        }
        return new Person(personFromRepo);
    }

    @Override
    public Person add(final Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
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
    public Person updatePhone(final Person person, final String phone) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot process updatePhone operation for null value argument.");
        }
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setPhone(phone);
        personRepository.updateInstance(personRepoInst);
        person.setPhone(phone);
        return person;
    }

    @Override
    public Person updateEmail(final Person person, final String email) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot process updateEmail operation for null value argument.");
        }
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setEmail(email);
        personRepository.updateInstance(personRepoInst);
        person.setEmail(email);
        return person;
    }

    @Override
    public Person updateAddress(final Person person, final Address address) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot process updateAddress operation for null value argument.");
        }
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setAddress(address);
        personRepository.updateInstance(personRepoInst);
        person.setAddress(address);
        return person;
    }

    @Override
    public void remove(final Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Cannot process remove operation for null value argument.");
        }
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepository.removeInstance(personRepoInst);
    }

    public Repository<Person, String> getPersonRepository() {
        return personRepository;
    }

    public void setPersonRepository(final Repository<Person, String> personRepository) {
        this.personRepository = personRepository;
    }
}
