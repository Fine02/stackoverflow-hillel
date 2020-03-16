package com.ra.course.com.stackoverflow.exception.service;

public class InternalServerErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(final String message) {
        super(message);
    }
}
