package com.ra.course.ams.airline.manag.system.exceptions;

public class PersonNotExistException extends InstanceNotExistException {

    private static final long serialVersionUID = 4L;

    public PersonNotExistException() {
        super();
    }

    public PersonNotExistException(final String message) {
        super(message);
    }

    public PersonNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersonNotExistException(final Throwable cause) {
        super(cause);
    }
}
