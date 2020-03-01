package com.ra.course.com.stackoverflow.exception.repository;

public class InternalServerErrorException extends Exception{
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(final String message) {
        super(message);
    }
}
