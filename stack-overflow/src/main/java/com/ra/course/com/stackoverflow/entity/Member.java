package com.ra.course.com.stackoverflow.entity;


import com.ra.course.com.stackoverflow.entity.enums.Badge;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {

    @EqualsAndHashCode.Include
    @ToString.Include
    private Account account;

    private Map<Badge, Set<Question>> questionBadges = new HashMap<>();

    private List<Question> questions = new ArrayList<>();

    private List<Answer> answers = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    private List<Notification> notifications = new ArrayList<>();

    private List<Long> upVotedQuestionsId = new ArrayList<>();

    private List<Long> upVotedAnswersId = new ArrayList<>();

    private List<Long> upVotedCommentsId = new ArrayList<>();

    private List<Long> downVotedQuestionsId = new ArrayList<>();

    private List<Long> downVotedAnswersId = new ArrayList<>();

    private List<Long> downVotedCommentsId = new ArrayList<>();

    public int getReputation() {
        return this.account.getReputation();
    }

    public String getEmail() {
        return this.account.getEmail();
    }

    public Long getId() {
        return this.account.getId();
    }

}
