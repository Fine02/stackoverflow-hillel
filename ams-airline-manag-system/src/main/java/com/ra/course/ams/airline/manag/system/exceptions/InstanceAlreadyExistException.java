package com.ra.course.ams.airline.manag.system.exceptions;

public class InstanceAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public InstanceAlreadyExistException() {
        super();
    }

    public InstanceAlreadyExistException(final String message) {
        super(message);
    }

    public InstanceAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InstanceAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
