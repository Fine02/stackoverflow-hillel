package com.ra.course.com.stackoverflow.exception.repository;

public class QuestionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QuestionNotFoundException() {
        super("No such question");
    }

    public QuestionNotFoundException(final String message) {
        super(message);
    }

}
