package com.ra.course.com.stackoverflow.exception.service;

public class MemberServiceException extends ServiceException{
    public MemberServiceException(String message) {
        super(message);
    }
    public MemberServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
