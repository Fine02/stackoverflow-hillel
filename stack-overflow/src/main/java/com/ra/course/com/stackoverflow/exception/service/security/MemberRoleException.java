package com.ra.course.com.stackoverflow.exception.service.security;

import com.ra.course.com.stackoverflow.entity.enums.AccountRole;

public class MemberRoleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MemberRoleException(final AccountRole role) {
        super("Member is " + role.toString());
    }

}
