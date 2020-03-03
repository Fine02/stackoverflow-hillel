package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class CustomScheduleRepository implements Repository<CustomSchedule, String> {

    private final List<CustomSchedule> customSchedules;

    public CustomScheduleRepository(List<CustomSchedule> customSchedules) {
        this.customSchedules = customSchedules;
    }

    boolean isAlreadyExist(String identifier) {
        return customSchedules.stream().map(CustomSchedule::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public CustomSchedule getInstance(String instanceId) {
        return customSchedules.stream()
                .filter(customScheduleItem -> customScheduleItem.getId().equals(instanceId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<CustomSchedule> getInstances() {
        return customSchedules;
    }

    @Override
    public CustomSchedule addInstance(CustomSchedule customSchedule) {
        if (isAlreadyExist(customSchedule.getId())) {
            throw new InstanceAlreadyExistException();
        }
        customSchedules.add(customSchedule);
        return customSchedule;
    }

    @Override
    public void updateInstance(CustomSchedule customSchedule) {
        if (!isAlreadyExist(customSchedule.getId())) {
            throw new InstanceNotExistException();
        }
        customSchedules.stream()
                .filter(customScheduleItem -> customScheduleItem.getId().equals(customSchedule.getId()))
                .forEach(customScheduleItemForUpdate -> customScheduleItemForUpdate = customSchedule);
    }

    @Override
    public void removeInstance(CustomSchedule customSchedule) {
        customSchedules.removeIf(customScheduleItem -> customScheduleItem.getId().equals(customSchedule.getId()));
    }
}
