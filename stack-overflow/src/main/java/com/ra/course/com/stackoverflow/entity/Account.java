package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private String password;

    @NonNull
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @Builder.Default
    private int reputation = 0;
}