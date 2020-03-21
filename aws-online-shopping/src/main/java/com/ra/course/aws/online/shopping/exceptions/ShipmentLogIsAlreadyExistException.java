package com.ra.course.aws.online.shopping.exceptions;

public class ShipmentLogIsAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ShipmentLogIsAlreadyExistException(final String message) {
        super(message);
    }
}
