package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class WeeklyScheduleRepository implements Repository<WeeklySchedule, String> {

    transient private final List<WeeklySchedule> weeklySchedules;

    public WeeklyScheduleRepository(final List<WeeklySchedule> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
    }

    public boolean isAlreadyExist(final String identifier) {
        return weeklySchedules.stream().map(WeeklySchedule::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public WeeklySchedule getInstance(final String instanceId) {
        return weeklySchedules.stream()
                .filter(weeklySchedItem -> weeklySchedItem.getId().equals(instanceId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<WeeklySchedule> getInstances() {
        return weeklySchedules;
    }

    @Override
    public WeeklySchedule addInstance(final WeeklySchedule weeklySchedule) {
        if (isAlreadyExist(weeklySchedule.getId())) {
            throw new InstanceAlreadyExistException();
        }
        weeklySchedules.add(weeklySchedule);
        return weeklySchedule;
    }

    @Override
    public void updateInstance(final WeeklySchedule weeklySchedule) {
        if (!isAlreadyExist(weeklySchedule.getId())) {
            throw new InstanceNotExistException();
        }
        weeklySchedules.stream()
                .filter(weeklyScheduleItm -> weeklyScheduleItm.getId().equals(weeklySchedule.getId()))
                .forEach(weekSchdItmForUpd -> weekSchdItmForUpd = weeklySchedule);
    }

    @Override
    public void removeInstance(final WeeklySchedule weeklySchedule) {
        weeklySchedules.removeIf(weekSchedItem -> weekSchedItem.getId().equals(weeklySchedule.getId()));
    }
}
