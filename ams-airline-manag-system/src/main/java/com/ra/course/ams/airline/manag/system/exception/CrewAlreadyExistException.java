package com.ra.course.ams.airline.manag.system.exception;

public class CrewAlreadyExistException extends InstanceAlreadyExistException {

    private static final long serialVersionUID = 4L;

    public CrewAlreadyExistException() {
        super();
    }

    public CrewAlreadyExistException(final String message) {
        super(message);
    }

    public CrewAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CrewAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
