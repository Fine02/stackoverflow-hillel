package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.util.Collection;
import java.util.Map;

public interface PilotsRepository  {

    Pilot getInstance(final String phoneNumber);

    Pilot findByPhoneNumber(final String phoneNumber);

    Pilot findByEmail(final String email);

    Collection<Pilot> getInstances();

    Pilot addInstance(final Pilot pilot);

    void updateInstance(final Pilot pilot);

    void removeInstance(final Pilot pilot);

    Map<String, Pilot> getPilots();

    void setPilots(final Map<String, Pilot> pilots);
}
