package com.ra.course.com.stackoverflow.exception.repository;

public class DataBaseOperationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataBaseOperationException(final String message) {
        super(message);
    }
}
