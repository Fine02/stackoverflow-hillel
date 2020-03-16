package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightScheduleService;

public class FlightScheduleServiceImpl implements FlightScheduleService {

    transient private final Repository<WeeklySchedule, String> weeklySchedRepo;
    transient private final Repository<CustomSchedule, String> customSchedRepo;

    public FlightScheduleServiceImpl(final Repository<WeeklySchedule, String> weeklySchedRepo, final Repository<CustomSchedule, String> customSchedRepo) {
        this.weeklySchedRepo = weeklySchedRepo;
        this.customSchedRepo = customSchedRepo;
    }

    @Override
    public WeeklySchedule addSchedule(final WeeklySchedule weeklySchedule) {
        if (weeklySchedule == null) {
            throw new IllegalArgumentException("Cannot procss add operation for null value argument.");
        }
        return weeklySchedRepo.addInstance(weeklySchedule);
    }

    @Override
    public WeeklySchedule updateSchedule(final WeeklySchedule weeklySchedule) {
        if (weeklySchedule == null) {
            throw new IllegalArgumentException("Cannot process update operation for null value argment.");
        }
        weeklySchedRepo.updateInstance(weeklySchedule);
        return weeklySchedule;
    }

    @Override
    public CustomSchedule addSchedule(final CustomSchedule customSchedule) {
        if (customSchedule == null) {
            throw new IllegalArgumentException("Cannot process add operation for null value argument.");
        }
        return customSchedRepo.addInstance(customSchedule);
    }

    @Override
    public CustomSchedule updateSchedule(final CustomSchedule customSchedule) {
        if (customSchedule == null) {
            throw new IllegalArgumentException("Cannot process update operation for null value argument.");
        }
        customSchedRepo.updateInstance(customSchedule);
        return customSchedule;
    }
}
