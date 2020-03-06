package com.ra.course.aws.online.shopping.exception;

public class ElementNotFoundException extends IllegalArgumentException{
    private static final long serialVersionUID = -7034897190745766939L;

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }


}
