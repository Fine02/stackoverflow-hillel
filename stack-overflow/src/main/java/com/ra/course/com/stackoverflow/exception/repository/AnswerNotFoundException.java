package com.ra.course.com.stackoverflow.exception.repository;


public class AnswerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AnswerNotFoundException() {
        super("No such answer");
    }

}
