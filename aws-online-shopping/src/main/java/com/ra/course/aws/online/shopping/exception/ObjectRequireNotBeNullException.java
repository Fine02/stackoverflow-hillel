package com.ra.course.aws.online.shopping.exception;

public class ObjectRequireNotBeNullException extends NullPointerException{
    private static final long serialVersionUID = 5162710183389028792L;
    public ObjectRequireNotBeNullException() {
        super();
    }

    public ObjectRequireNotBeNullException(final String s) {
        super(s);
    }
}
