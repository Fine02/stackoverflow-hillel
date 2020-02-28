package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Person;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class PersonsRepository implements Repository<Person, String> {

    private Map<String, Person> persons;

    @Override
    public Person getInstance(String phoneNumber) {
        return persons.get(phoneNumber);
    }

    @Override
    public Collection<Person> getInstances() {
        return persons.values();
    }

    @Override
    public Person addInstance(Person person) {
        if (persons.containsKey(person.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return persons.put(person.getPhone(), person);
    }

    @Override
    public void updateInstance(Person person) {
        persons.put(person.getPhone(), person);
    }

    @Override
    public void removeInstance(Person person) {
        persons.remove(person.getPhone());
    }

    public Map<String, Person> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, Person> persons) {
        this.persons = persons;
    }
}
