package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "email", "role", "status"})
public class Account {

    private Long id;

    private String email;

    private String name;

    private String password;

    private AccountRole role = AccountRole.USER;

    private AccountStatus status = AccountStatus.ACTIVE;

    private int reputation;
}