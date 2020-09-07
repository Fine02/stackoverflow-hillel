package com.ra.course.com.stackoverflow.exception.repository;

public class MemberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException() {
        super("No such member");
    }

    public MemberNotFoundException(final String message) {
        super(message);
    }

}
