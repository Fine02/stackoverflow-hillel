package com.ra.course.com.stackoverflow.exception.service;

public class LoginException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LoginException(final String message) {
        super(message);
    }
}
