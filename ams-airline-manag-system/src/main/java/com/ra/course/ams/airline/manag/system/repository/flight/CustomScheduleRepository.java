package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;

import java.util.Collection;

public interface CustomScheduleRepository {

    boolean isAlreadyExist(final String identifier);

    CustomSchedule getInstance(final String instanceId);

    Collection<CustomSchedule> getInstances();

    CustomSchedule addInstance(final CustomSchedule customSchedule);

    void updateInstance(final CustomSchedule customSchedule);

    void removeInstance(final CustomSchedule customSchedule);
}
