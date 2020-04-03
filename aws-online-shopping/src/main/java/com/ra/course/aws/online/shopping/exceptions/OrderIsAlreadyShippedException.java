package com.ra.course.aws.online.shopping.exceptions;

public class OrderIsAlreadyShippedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public OrderIsAlreadyShippedException(final String message) {
        super(message);
    }
}
