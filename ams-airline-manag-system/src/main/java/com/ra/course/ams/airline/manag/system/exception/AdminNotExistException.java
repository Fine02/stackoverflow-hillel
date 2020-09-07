package com.ra.course.ams.airline.manag.system.exception;

public class AdminNotExistException extends InstanceNotExistException {

    private static final long serialVersionUID = 4L;

    public AdminNotExistException() {
        super();
    }

    public AdminNotExistException(final String message) {
        super(message);
    }

    public AdminNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AdminNotExistException(final Throwable cause) {
        super(cause);
    }
}
