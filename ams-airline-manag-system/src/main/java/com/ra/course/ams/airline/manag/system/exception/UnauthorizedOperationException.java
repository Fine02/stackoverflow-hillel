package com.ra.course.ams.airline.manag.system.exception;

public class UnauthorizedOperationException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public UnauthorizedOperationException() {
        super();
    }

    public UnauthorizedOperationException(final String message) {
        super(message);
    }

    public UnauthorizedOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedOperationException(final Throwable cause) {
        super(cause);
    }
}
