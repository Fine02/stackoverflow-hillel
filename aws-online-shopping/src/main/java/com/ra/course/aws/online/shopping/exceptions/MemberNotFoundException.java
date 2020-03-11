package com.ra.course.aws.online.shopping.exceptions;

public class MemberNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(final String message) {
        super(message);
    }
}
