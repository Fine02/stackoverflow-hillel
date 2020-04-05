package com.ra.course.ams.airline.manag.system.exception;

public class CrewNotExistException extends InstanceNotExistException {

    private static final long serialVersionUID = 4L;

    public CrewNotExistException() {
        super();
    }

    public CrewNotExistException(final String message) {
        super(message);
    }

    public CrewNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CrewNotExistException(final Throwable cause) {
        super(cause);
    }
}
