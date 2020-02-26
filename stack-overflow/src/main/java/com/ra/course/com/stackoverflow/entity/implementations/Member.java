package com.ra.course.com.stackoverflow.entity.implementations;


import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

public class Member implements IDEntity {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private Account account;

    @NonNull
    final private List<Badge> badges;

    @NonNull
    final private List<Question> questions;

    @NonNull
    final private List<Answer> answers;

    @NonNull
    final private List<Comment> comments;

    @NonNull
    final private List<Notification> notifications;

    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }
}
