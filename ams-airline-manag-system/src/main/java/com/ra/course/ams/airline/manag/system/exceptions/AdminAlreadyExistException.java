package com.ra.course.ams.airline.manag.system.exceptions;

public class AdminAlreadyExistException extends InstanceAlreadyExistException {

    private static final long serialVersionUID = 4L;

    public AdminAlreadyExistException() {
        super();
    }

    public AdminAlreadyExistException(final String message) {
        super(message);
    }

    public AdminAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AdminAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
