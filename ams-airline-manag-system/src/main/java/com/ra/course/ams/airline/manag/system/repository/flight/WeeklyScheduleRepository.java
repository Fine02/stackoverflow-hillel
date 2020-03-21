package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;

import java.util.Collection;

public interface WeeklyScheduleRepository {

    boolean isAlreadyExist(final String identifier);

    WeeklySchedule getInstance(final String instanceId);

    Collection<WeeklySchedule> getInstances();

    WeeklySchedule addInstance(final WeeklySchedule weeklySchedule);

    void updateInstance(final WeeklySchedule weeklySchedule);

    void removeInstance(final WeeklySchedule weeklySchedule);
}
