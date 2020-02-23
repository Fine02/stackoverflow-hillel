package com.ra.course.com.stackoverflow.exception.service;

public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
