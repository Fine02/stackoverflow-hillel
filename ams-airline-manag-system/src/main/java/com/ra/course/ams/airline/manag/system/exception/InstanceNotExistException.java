package com.ra.course.ams.airline.manag.system.exception;

public class InstanceNotExistException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public InstanceNotExistException() {
        super();
    }

    public InstanceNotExistException(final String message) {
        super(message);
    }

    public InstanceNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InstanceNotExistException(final Throwable cause) {
        super(cause);
    }
}
