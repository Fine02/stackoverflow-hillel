package com.ra.course.com.stackoverflow.exception.repository;

public class QuestionRepositoryException extends Exception {
    private static final long serialVersionUID = 1L;

    public QuestionRepositoryException(final String message) {
        super(message);
    }

    public QuestionRepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
