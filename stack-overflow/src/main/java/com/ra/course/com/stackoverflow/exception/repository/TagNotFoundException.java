package com.ra.course.com.stackoverflow.exception.repository;

public class TagNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TagNotFoundException() {
        super("No such tag");
    }

}
