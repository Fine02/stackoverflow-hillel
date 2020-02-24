package com.ra.course.com.stackoverflow.exception.service;

public class MemberServiceException extends ServiceException{
    private static final long serialVersionUID = 1L;
    public MemberServiceException(final String message) {
        super(message);
    }
    public MemberServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
