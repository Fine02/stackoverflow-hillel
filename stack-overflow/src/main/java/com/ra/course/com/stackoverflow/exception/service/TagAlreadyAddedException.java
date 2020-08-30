package com.ra.course.com.stackoverflow.exception.service;

public class TagAlreadyAddedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TagAlreadyAddedException(final String message) {
        super(message);
    }

}
