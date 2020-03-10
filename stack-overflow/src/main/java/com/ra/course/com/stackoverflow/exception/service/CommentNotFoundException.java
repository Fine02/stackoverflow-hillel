package com.ra.course.com.stackoverflow.exception.service;

public class CommentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommentNotFoundException(final String message) {
        super(message);
    }

    public CommentNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
