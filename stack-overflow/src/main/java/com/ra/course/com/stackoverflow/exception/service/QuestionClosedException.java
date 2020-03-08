package com.ra.course.com.stackoverflow.exception.service;

public class QuestionClosedException extends Exception {

    private static final long serialVersionUID = 1L;

    public QuestionClosedException(final String message) {
        super(message);
    }

    public QuestionClosedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
