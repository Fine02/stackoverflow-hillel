package com.ra.course.ams.airline.manag.system.exceptions;

public class PersonAlreadyExistException extends InstanceAlreadyExistException {

    private static final long serialVersionUID = 4L;

    public PersonAlreadyExistException() {
        super();
    }

    public PersonAlreadyExistException(final String message) {
        super(message);
    }

    public PersonAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersonAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
