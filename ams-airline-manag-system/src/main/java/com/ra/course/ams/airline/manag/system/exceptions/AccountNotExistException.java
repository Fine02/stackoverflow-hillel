package com.ra.course.ams.airline.manag.system.exceptions;

public class AccountNotExistException extends InstanceNotExistException {
    public AccountNotExistException() {
        super();
    }

    public AccountNotExistException(String message) {
        super(message);
    }

    public AccountNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistException(Throwable cause) {
        super(cause);
    }
}
