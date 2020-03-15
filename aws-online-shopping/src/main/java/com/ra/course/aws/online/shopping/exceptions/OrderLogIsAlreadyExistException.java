package com.ra.course.aws.online.shopping.exceptions;

public class OrderLogIsAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrderLogIsAlreadyExistException(final String message) {
        super(message);
    }
}
