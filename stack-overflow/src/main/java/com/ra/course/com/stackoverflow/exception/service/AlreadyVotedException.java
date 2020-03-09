package com.ra.course.com.stackoverflow.exception.service;

public class AlreadyVotedException extends Exception {
    private static final long serialVersionUID = 1L;

    public AlreadyVotedException(final String message) {
        super(message);
    }

    public AlreadyVotedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
