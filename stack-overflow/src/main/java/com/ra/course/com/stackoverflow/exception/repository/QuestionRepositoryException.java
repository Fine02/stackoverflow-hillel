package com.ra.course.com.stackoverflow.exception.repository;

public class QuestionRepositoryException extends RepositoryException {
    public QuestionRepositoryException(String message) {
        super(message);
    }

    public QuestionRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
