package com.ra.course.com.stackoverflow.exception.service;

public class MemberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(final String message) {
        super(message);
    }

    public MemberNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
