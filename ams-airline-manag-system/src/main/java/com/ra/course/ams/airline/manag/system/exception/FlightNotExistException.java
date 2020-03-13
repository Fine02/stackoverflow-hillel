package com.ra.course.ams.airline.manag.system.exception;

public class FlightNotExistException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public FlightNotExistException(String message) {
        super(message);
    }
}
