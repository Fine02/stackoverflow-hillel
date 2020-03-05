package com.ra.course.ams.airline.manag.system.exception;

public class AccountNotExistException extends InstanceNotExistException {

    private static final long serialVersionUID = 4L;

    public AccountNotExistException() {
        super();
    }

    public AccountNotExistException(final String message) {
        super(message);
    }

    public AccountNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistException(final Throwable cause) {
        super(cause);
    }
}
