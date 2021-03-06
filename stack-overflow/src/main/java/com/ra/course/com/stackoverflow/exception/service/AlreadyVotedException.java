package com.ra.course.com.stackoverflow.exception.service;

public class AlreadyVotedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlreadyVotedException(final String message) {
        super(message + " is already voted");
    }

}
