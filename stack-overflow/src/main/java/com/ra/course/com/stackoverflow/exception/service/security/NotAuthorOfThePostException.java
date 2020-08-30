package com.ra.course.com.stackoverflow.exception.service.security;

public class NotAuthorOfThePostException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAuthorOfThePostException() {
        super("Not author of the post");
    }
}
