package com.ra.course.com.stackoverflow.exception.service;

public class AcceptedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AcceptedException(final String message) {
        super(message);
    }
}
