package com.ra.course.com.stackoverflow.exception.service;

public class CannotVoteOwnPostException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CannotVoteOwnPostException(final String message) {
        super(message);
    }

    public CannotVoteOwnPostException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
