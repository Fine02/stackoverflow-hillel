package com.ra.course.com.stackoverflow.exception.service;

public class AlreadyExistAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistAccountException(final String message) {
        super(message);
    }
}
