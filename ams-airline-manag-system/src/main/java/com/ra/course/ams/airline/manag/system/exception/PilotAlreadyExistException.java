package com.ra.course.ams.airline.manag.system.exception;

public class PilotAlreadyExistException extends InstanceAlreadyExistException {

    private static final long serialVersionUID = 4L;

    public PilotAlreadyExistException() {
        super();
    }

    public PilotAlreadyExistException(final String message) {
        super(message);
    }

    public PilotAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PilotAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
