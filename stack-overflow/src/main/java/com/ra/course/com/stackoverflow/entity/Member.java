package com.ra.course.com.stackoverflow.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private Account account;

    @NonNull
    @Builder.Default
    private List<Badge> badges = new ArrayList<>();

    @NonNull
    @Builder.Default
    @ToString.Exclude // to avoid circular dependency between Question and Member toString()
    private List<Question> questions = new ArrayList<>();

    @NonNull
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();

    @NonNull
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @NonNull
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();


    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
}
