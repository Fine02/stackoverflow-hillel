package com.ra.course.aws.online.shopping.exceptions;

public class MemberNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(final String message) {
        super(message);
    }
}
