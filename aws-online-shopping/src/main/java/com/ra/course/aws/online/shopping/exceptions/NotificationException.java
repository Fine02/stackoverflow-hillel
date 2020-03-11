package com.ra.course.aws.online.shopping.exceptions;

public class NotificationException extends Exception{
    private static final long serialVersionUID = 1L;

    public NotificationException(final String message) {
        super(message);
    }
}
