package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.Address;
import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exception.PersonAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.PersonNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.PersonsRepository;
import com.ra.course.ams.airline.manag.system.service.PersonManagementService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonManagementServiceImpl implements PersonManagementService<Person> {

    transient private final PersonsRepository personRepository;

    public PersonManagementServiceImpl(final PersonsRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findByEmail(final String email) {

        return Optional.of(new Person(personRepository.getInstances().stream()
                .filter(person -> email.equals(person.getEmail()))
                .findAny()
                .orElseThrow(() -> new PersonNotExistException("No person found for given email"))));
    }

    @Override
    public Optional<Person> findByPhoneNumber(final String phone) {
        final Person personFromRepo = personRepository.getInstance(phone);
        if (personFromRepo == null) {
            throw new PersonNotExistException("No person found for given phone number");
        }
        return Optional.of(new Person(personFromRepo));
    }

    @Override
    public Optional<Person> add(final Person person) {
        Person personFromRepo = personRepository.getInstance(person.getPhone());
        if (personFromRepo != null) {
            throw new PersonAlreadyExistException();
        }
        personFromRepo = new Person(person);
        personRepository.addInstance(personFromRepo);
        return Optional.of(person);
    }

    @Override
    public Optional<Person> updatePhone(final Person person, final String phone) {
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setPhone(phone);
        personRepository.updateInstance(personRepoInst);
        person.setPhone(phone);
        return Optional.of(person);
    }

    @Override
    public Optional<Person> updateEmail(final Person person, final String email) {
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setEmail(email);
        personRepository.updateInstance(personRepoInst);
        person.setEmail(email);
        return Optional.of(person);
    }

    @Override
    public Optional<Person> updateAddress(final Person person, final Address address) {
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepoInst.setAddress(address);
        personRepository.updateInstance(personRepoInst);
        person.setAddress(address);
        return Optional.of(person);
    }

    @Override
    public void remove(final Person person) {
        final Person personRepoInst = personRepository.getInstance(person.getPhone());
        if (personRepoInst == null) {
            throw new PersonNotExistException();
        }
        personRepository.removeInstance(personRepoInst);
    }

}
