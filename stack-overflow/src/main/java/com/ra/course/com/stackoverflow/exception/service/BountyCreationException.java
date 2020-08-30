package com.ra.course.com.stackoverflow.exception.service;

public class BountyCreationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BountyCreationException(final String message) {
        super(message);
    }
}
