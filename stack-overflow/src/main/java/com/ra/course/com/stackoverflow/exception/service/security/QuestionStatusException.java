package com.ra.course.com.stackoverflow.exception.service.security;

import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;

public class QuestionStatusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuestionStatusException(final QuestionStatus status) {
        super("Question is " + status.toString());
    }

    public QuestionStatusException(final String message, final QuestionStatus status) {
        super("Can't " + message + ", question is " + status.toString());
    }
}
