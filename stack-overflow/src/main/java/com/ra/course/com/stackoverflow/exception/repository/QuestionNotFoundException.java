package com.ra.course.com.stackoverflow.exception.repository;

public class QuestionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QuestionNotFoundException(final String message) {
        super(message);
    }

    public QuestionNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
