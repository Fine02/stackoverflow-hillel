package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Crew;

import java.util.Collection;
import java.util.Map;

public interface CrewRepository {

    Crew getInstance(final String phoneNumber);

    Collection<Crew> getInstances();

    Crew addInstance(final Crew crew) ;

    void updateInstance(final Crew crew);

    void removeInstance(final Crew crew);

    Map<String, Crew> getCrews();

    void setCrews(final Map<String, Crew> crews);
}
