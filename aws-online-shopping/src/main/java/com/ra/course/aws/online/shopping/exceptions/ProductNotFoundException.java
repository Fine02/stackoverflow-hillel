package com.ra.course.aws.online.shopping.exceptions;

public class ProductNotFoundException extends  RuntimeException {
    private static final long serialVersionUID = -7034897190745766929L;
    public ProductNotFoundException(final String message) {
        super(message);
    }
}
