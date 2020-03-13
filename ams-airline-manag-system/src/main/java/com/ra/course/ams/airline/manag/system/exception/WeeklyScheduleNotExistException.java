package com.ra.course.ams.airline.manag.system.exception;

public class WeeklyScheduleNotExistException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public WeeklyScheduleNotExistException(String message) {
        super(message);
    }
}
