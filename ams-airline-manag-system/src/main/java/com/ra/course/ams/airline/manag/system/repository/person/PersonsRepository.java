package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Pilot;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class PersonsRepository implements Repository<Pilot, String> {

    private Map<String, Pilot> persons;

    @Override
    public Pilot getInstance(String phoneNumber) {
        return persons.get(phoneNumber);
    }

    @Override
    public Collection<Pilot> getInstances() {
        return persons.values();
    }

    @Override
    public Pilot addInstance(Pilot person) {
        if (persons.containsKey(person.getPhone())){
            throw new InstanceAlreadyExistException();
        }
        return persons.put(person.getPhone(), person);
    }

    @Override
    public void updateInstance(Pilot person) {
        persons.put(person.getPhone(), person);
    }

    @Override
    public void removeInstance(Pilot person) {
        persons.remove(person.getPhone());
    }

    public Map<String, Pilot> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, Pilot> persons) {
        this.persons = persons;
    }
}
