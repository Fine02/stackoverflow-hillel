package com.ra.course.com.stackoverflow.exception.service;

public class CannotVoteOwnPostException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CannotVoteOwnPostException() {
        super("Cannot vote own post");
    }

}
