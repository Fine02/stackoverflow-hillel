package com.ra.course.com.stackoverflow.exception.service;

public class TagNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TagNotFoundException(final String message) {
        super(message);
    }

    public TagNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
