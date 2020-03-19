package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.util.Collection;
import java.util.Map;

public interface PersonsRepository {

    Pilot getInstance(final String phoneNumber);

    Collection<Pilot> getInstances();

    Pilot addInstance(final Pilot person);


    void updateInstance(final Pilot person);


    void removeInstance(final Pilot person);

    Map<String, Pilot> getPersons();

    void setPersons(final Map<String, Pilot> persons);
}
