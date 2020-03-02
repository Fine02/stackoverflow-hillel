package com.ra.course.ams.airline.manag.system.exceptions;

public class ReservationWasNotModifiedException extends RuntimeException {
    public ReservationWasNotModifiedException() {
    }

    public ReservationWasNotModifiedException(String message) {
        super(message);
    }
}
