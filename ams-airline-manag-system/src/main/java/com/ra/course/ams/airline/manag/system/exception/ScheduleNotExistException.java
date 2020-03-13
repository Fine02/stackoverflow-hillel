package com.ra.course.ams.airline.manag.system.exception;

public class ScheduleNotExistException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public ScheduleNotExistException(String message) {
        super(message);
    }
}
