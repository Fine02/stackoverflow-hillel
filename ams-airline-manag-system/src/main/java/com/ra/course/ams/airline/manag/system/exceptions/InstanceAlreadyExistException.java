package com.ra.course.ams.airline.manag.system.exceptions;

public class InstanceAlreadyExistException extends RuntimeException {
    public InstanceAlreadyExistException() {
        super();
    }

    public InstanceAlreadyExistException(String message) {
        super(message);
    }

    public InstanceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
