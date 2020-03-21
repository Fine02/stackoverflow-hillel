package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;

import java.util.Optional;

public interface FlightScheduleService {

    Optional<WeeklySchedule> addSchedule(WeeklySchedule weeklySchedule);

    Optional<WeeklySchedule> updateSchedule(WeeklySchedule weeklySchedule);

    Optional<CustomSchedule> addSchedule(CustomSchedule customSchedule);

    Optional<CustomSchedule> updateSchedule(CustomSchedule customSchedule);
}
