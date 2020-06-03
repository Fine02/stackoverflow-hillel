package com.ra.course.com.stackoverflow.exception.service;

public class WrongPasswordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongPasswordException(final String message) {
        super(message);
    }
}
