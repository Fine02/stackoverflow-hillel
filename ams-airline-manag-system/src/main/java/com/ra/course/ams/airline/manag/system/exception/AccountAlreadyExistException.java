package com.ra.course.ams.airline.manag.system.exception;

public class AccountAlreadyExistException extends InstanceAlreadyExistException {

    private static final long serialVersionUID = 4L;

    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(final String message) {
        super(message);
    }

    public AccountAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
