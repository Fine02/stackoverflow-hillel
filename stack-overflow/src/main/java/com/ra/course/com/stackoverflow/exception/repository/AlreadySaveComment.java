package com.ra.course.com.stackoverflow.exception.repository;

public class AlreadySaveComment extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlreadySaveComment(final String message) {
        super(message);
    }

    public AlreadySaveComment(final String message, final Throwable cause) {
        super(message, cause);
    }
}
