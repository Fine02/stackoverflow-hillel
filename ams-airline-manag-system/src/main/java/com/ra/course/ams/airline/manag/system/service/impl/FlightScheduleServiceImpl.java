package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightScheduleService;

public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final Repository<WeeklySchedule, String> weeklyScheduleRepository;
    private final Repository<CustomSchedule, String> customScheduleRepository;

    public FlightScheduleServiceImpl(Repository<WeeklySchedule, String> weeklyScheduleRepository, Repository<CustomSchedule, String> customScheduleRepository) {
        this.weeklyScheduleRepository = weeklyScheduleRepository;
        this.customScheduleRepository = customScheduleRepository;
    }

    @Override
    public WeeklySchedule addSchedule(WeeklySchedule weeklySchedule) {
        if (weeklySchedule == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return weeklyScheduleRepository.addInstance(weeklySchedule);
    }

    @Override
    public WeeklySchedule updateSchedule(WeeklySchedule weeklySchedule) {
        if (weeklySchedule == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        weeklyScheduleRepository.updateInstance(weeklySchedule);
        return weeklySchedule;
    }

    @Override
    public CustomSchedule addSchedule(CustomSchedule customSchedule) {
        if (customSchedule == null) {
            throw new NullPointerException("Cannot process add operation for null value argument.");
        }
        return customScheduleRepository.addInstance(customSchedule);
    }

    @Override
    public CustomSchedule updateSchedule(CustomSchedule customSchedule) {
        if (customSchedule == null) {
            throw new NullPointerException("Cannot process update operation for null value argument.");
        }
        customScheduleRepository.updateInstance(customSchedule);
        return customSchedule;
    }
}
