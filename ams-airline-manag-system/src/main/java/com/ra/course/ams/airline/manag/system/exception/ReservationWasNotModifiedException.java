package com.ra.course.ams.airline.manag.system.exception;

public class ReservationWasNotModifiedException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public ReservationWasNotModifiedException(final String message) {
        super(message);
    }

    public ReservationWasNotModifiedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}


