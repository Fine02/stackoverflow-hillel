package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class WeeklyScheduleRepository implements Repository<WeeklySchedule, String> {

    private final List<WeeklySchedule> weeklySchedules;

    public WeeklyScheduleRepository(List<WeeklySchedule> weeklySchedules) {
        this.weeklySchedules = weeklySchedules;
    }

    boolean isAlreadyExist(String identifier) {
        return weeklySchedules.stream().map(WeeklySchedule::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public WeeklySchedule getInstance(String instanceId) {
        return weeklySchedules.stream()
                .filter(weeklyScheduleItem -> weeklyScheduleItem.getId().equals(instanceId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<WeeklySchedule> getInstances() {
        return weeklySchedules;
    }

    @Override
    public WeeklySchedule addInstance(WeeklySchedule weeklySchedule) {
        if (isAlreadyExist(weeklySchedule.getId())) {
            throw new InstanceAlreadyExistException();
        }
        weeklySchedules.add(weeklySchedule);
        return weeklySchedule;
    }

    @Override
    public void updateInstance(WeeklySchedule weeklySchedule) {
        if (!isAlreadyExist(weeklySchedule.getId())) {
            throw new InstanceNotExistException();
        }
        weeklySchedules.stream()
                .filter(weeklyScheduleItem -> weeklyScheduleItem.getId().equals(weeklySchedule.getId()))
                .forEach(weeklyScheduleItemForUpdate -> weeklyScheduleItemForUpdate = weeklySchedule);
    }

    @Override
    public void removeInstance(WeeklySchedule weeklySchedule) {
        weeklySchedules.removeIf(weeklyScheduleItem -> weeklyScheduleItem.getId().equals(weeklySchedule.getId()));
    }
}
