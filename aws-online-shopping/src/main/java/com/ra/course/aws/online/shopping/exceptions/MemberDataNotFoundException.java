package com.ra.course.aws.online.shopping.exceptions;

public class MemberDataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MemberDataNotFoundException(final String message) {
        super(message);
    }
}
