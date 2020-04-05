package com.ra.course.aws.online.shopping.exceptions;

public class PaymentNotProvidedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PaymentNotProvidedException(final String message) {
        super(message);
    }
}
