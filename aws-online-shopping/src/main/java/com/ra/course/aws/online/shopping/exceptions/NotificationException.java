package com.ra.course.aws.online.shopping.exceptions;

public class NotificationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotificationException(final String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
