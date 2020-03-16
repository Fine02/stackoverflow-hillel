package com.ra.course.ams.airline.manag.system.repository.flight;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.exception.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.List;

public class CustomScheduleRepository implements Repository<CustomSchedule, String> {

    transient private final List<CustomSchedule> customSchedules;

    public CustomScheduleRepository(final List<CustomSchedule> customSchedules) {
        this.customSchedules = customSchedules;
    }

    public boolean isAlreadyExist(final String identifier) {
        return customSchedules.stream().map(CustomSchedule::getId)
                .anyMatch(id -> id.equals(identifier));
    }

    @Override
    public CustomSchedule getInstance(final String instanceId) {
        return customSchedules.stream()
                .filter(customSchedItem -> customSchedItem.getId().equals(instanceId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<CustomSchedule> getInstances() {
        return customSchedules;
    }

    @Override
    public CustomSchedule addInstance(final CustomSchedule customSchedule) {
        if (isAlreadyExist(customSchedule.getId())) {
            throw new InstanceAlreadyExistException();
        }
        customSchedules.add(customSchedule);
        return customSchedule;
    }

    @Override
    public void updateInstance(final CustomSchedule customSchedule) {
        if (!isAlreadyExist(customSchedule.getId())) {
            throw new InstanceNotExistException();
        }
        customSchedules.stream()
                .filter(customSchedItem -> customSchedItem.getId().equals(customSchedule.getId()))
                .forEach(customSchdItemUpd -> customSchdItemUpd = customSchedule);
    }

    @Override
    public void removeInstance(final CustomSchedule customSchedule) {
        customSchedules.removeIf(customSchedItem -> customSchedItem.getId().equals(customSchedule.getId()));
    }
}
