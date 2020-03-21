package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Person;

import java.util.Collection;
import java.util.Map;

public interface PersonsRepository {

    Person getInstance(final String phoneNumber);

    Collection<Person> getInstances();

    Person addInstance(final Person person);

    void updateInstance(final Person person);

    void removeInstance(final Person person);

    Map<String, Person> getPersons();

    void setPersons(final Map<String, Person> persons);
}
