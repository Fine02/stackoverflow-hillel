package com.ra.course.com.stackoverflow.exception.service;

public class AlreadyExistAccount extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyExistAccount(final String message) {
        super(message);
    }
}
