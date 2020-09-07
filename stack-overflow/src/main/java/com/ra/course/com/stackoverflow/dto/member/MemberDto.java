package com.ra.course.com.stackoverflow.dto.member;

import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberDto {

    private Long id;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String email;

    @EqualsAndHashCode.Include
    private AccountRole role;

    @EqualsAndHashCode.Include
    private AccountStatus status;

    private int reputation;

    private int questions;

    private int answers;

    private int comments;

}
