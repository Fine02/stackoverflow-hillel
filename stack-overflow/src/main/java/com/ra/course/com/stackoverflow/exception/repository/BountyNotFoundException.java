package com.ra.course.com.stackoverflow.exception.repository;

public class BountyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BountyNotFoundException() {
        super("No such bounty");
    }
}
