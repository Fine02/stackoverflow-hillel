package com.ra.course.com.stackoverflow.exception.service.security;

import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;

public class MemberStatusException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MemberStatusException(final AccountStatus status) {
        super("Member status is " + status.toString());
    }

}
