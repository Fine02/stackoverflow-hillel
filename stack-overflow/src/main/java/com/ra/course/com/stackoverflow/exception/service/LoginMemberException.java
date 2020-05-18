package com.ra.course.com.stackoverflow.exception.service;

public class LoginMemberException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LoginMemberException(final String message) {
        super(message);
    }
}
