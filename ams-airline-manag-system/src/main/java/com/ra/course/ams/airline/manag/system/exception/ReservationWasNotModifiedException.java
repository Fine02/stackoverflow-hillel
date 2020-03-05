package com.ra.course.ams.airline.manag.system.exception;

public class ReservationWasNotModifiedException extends RuntimeException {

    public ReservationWasNotModifiedException(String message) {
        super(message);
    }

    public ReservationWasNotModifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}


