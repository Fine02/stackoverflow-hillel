package com.ra.course.com.stackoverflow.exception.service;


public class AnswerNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public AnswerNotFoundException(final String message) {
        super(message);
    }

    public AnswerNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
