package com.ra.course.aws.online.shopping.exceptions;

public class ElementNotFoundException extends IllegalArgumentException{
    private static final long serialVersionUID = -1L;

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


}
