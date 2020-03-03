package com.ra.course.com.stackoverflow.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @NonNull
    private List<Long> upVotedQuestionsId = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Long> upVotedAnswersId = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Long> upVotedCommentsId = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Long> downVotedQuestionsId = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Long> downVotedAnswersId = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Long> downVotedCommentsId = new ArrayList<>();


    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }

}
