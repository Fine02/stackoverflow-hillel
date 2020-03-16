package com.ra.course.ams.airline.manag.system.exception;

public class BadCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(final String message) {
        super(message);
    }

    public BadCredentialsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadCredentialsException(final Throwable cause) {
        super(cause);
    }
}
