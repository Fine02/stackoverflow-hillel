package com.ra.course.com.stackoverflow.entity.implementations;


import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member implements IDEntity {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private Account account;

    @NonNull
    private List<Badge> badges;

    @NonNull
    private List<Question> questions;

    @NonNull
    private List<Answer> answers;

    @NonNull
    private List<Comment> comments;

    @NonNull
    private List<Notification> notifications;

    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }
}
