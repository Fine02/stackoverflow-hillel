package com.ra.course.com.stackoverflow.exception.repository;

public class CommentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommentNotFoundException() {
        super("No such comment");
    }

}
