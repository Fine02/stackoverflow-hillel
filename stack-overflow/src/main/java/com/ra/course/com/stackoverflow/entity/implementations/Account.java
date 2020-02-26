package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.*;
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
    private AccountStatus status;

    @NonNull
    private String name;

    @NonNull
    private String email;

    private int reputation;
}