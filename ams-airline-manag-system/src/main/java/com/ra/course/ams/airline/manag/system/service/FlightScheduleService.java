package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;

public interface FlightScheduleService {

    WeeklySchedule addSchedule(WeeklySchedule weeklySchedule);
    WeeklySchedule updateSchedule(WeeklySchedule weeklySchedule);
    CustomSchedule addSchedule(CustomSchedule customSchedule);
    CustomSchedule updateSchedule(CustomSchedule customSchedule);

}
