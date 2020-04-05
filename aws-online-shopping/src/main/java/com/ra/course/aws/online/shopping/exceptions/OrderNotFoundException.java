package com.ra.course.aws.online.shopping.exceptions;

public class OrderNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(final String message) {
        super(message);
    }
}