package com.ra.course.ams.airline.manag.system.exceptions;

public class AccountAlreadyExistException extends InstanceAlreadyExistException {
    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }

    public AccountAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
