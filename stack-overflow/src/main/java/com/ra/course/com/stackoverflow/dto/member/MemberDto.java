package com.ra.course.com.stackoverflow.dto.member;

import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.Data;

@Data
public class MemberDto {

    private Long id;

    private String name;

    private String email;

    private AccountRole role;

    private AccountStatus status;

    private int reputation;

    private int questions;

    private int answers;

    private int comments;

}
