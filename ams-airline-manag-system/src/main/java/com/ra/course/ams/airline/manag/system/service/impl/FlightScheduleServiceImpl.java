package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.flight.CustomScheduleRepository;
import com.ra.course.ams.airline.manag.system.repository.flight.WeeklyScheduleRepository;
import com.ra.course.ams.airline.manag.system.service.FlightScheduleService;

import java.util.Optional;

public class FlightScheduleServiceImpl implements FlightScheduleService {

    transient private final WeeklyScheduleRepository weeklySchedRepo;
    transient private final CustomScheduleRepository customSchedRepo;

    public FlightScheduleServiceImpl(final WeeklyScheduleRepository weeklySchedRepo, final CustomScheduleRepository customSchedRepo) {
        this.weeklySchedRepo = weeklySchedRepo;
        this.customSchedRepo = customSchedRepo;
    }

    @Override
    public Optional<WeeklySchedule> addSchedule(final WeeklySchedule weeklySchedule) {

        return Optional.of(weeklySchedRepo.addInstance(weeklySchedule));
    }

    @Override
    public  Optional <WeeklySchedule> updateSchedule(final WeeklySchedule weeklySchedule) {
        weeklySchedRepo.updateInstance(weeklySchedule);

        return Optional.of(weeklySchedule);
    }

    @Override
    public  Optional <CustomSchedule> addSchedule(final CustomSchedule customSchedule) {

        return Optional.of(customSchedRepo.addInstance(customSchedule));
    }

    @Override
    public  Optional <CustomSchedule> updateSchedule(final CustomSchedule customSchedule) {
        customSchedRepo.updateInstance(customSchedule);

        return Optional.of(customSchedule);
    }
}
