package com.ra.course.ams.airline.manag.system.exceptions;

public class PilotNotExistException extends InstanceNotExistException {

    private static final long serialVersionUID = 4L;

    public PilotNotExistException() {
        super();
    }

    public PilotNotExistException(final String message) {
        super(message);
    }

    public PilotNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PilotNotExistException(final Throwable cause) {
        super(cause);
    }
}
