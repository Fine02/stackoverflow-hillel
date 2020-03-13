package com.ra.course.aws.online.shopping.exceptions;

public class ShippingAddressNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ShippingAddressNotFoundException(final String message) {
        super(message);
    }
}
