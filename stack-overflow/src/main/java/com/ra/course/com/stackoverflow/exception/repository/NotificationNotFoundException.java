package com.ra.course.com.stackoverflow.exception.repository;

public class NotificationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotificationNotFoundException() {
        super("No such notification");
    }

}
