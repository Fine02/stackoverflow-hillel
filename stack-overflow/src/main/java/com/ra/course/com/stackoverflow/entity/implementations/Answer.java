package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Answer implements IDEntity {

    @EqualsAndHashCode.Include
    @Getter
    final private long id;

    @NonNull
    @Getter
    @Setter
    private String answerText;

    @Getter
    @Setter
    private boolean accepted;

    @Getter
    @Setter
    private int voteCount;

    @Getter
    private int flagCount;

    @NonNull
    @Getter
    private final LocalDateTime creationDate;

    @NonNull
    @Getter
    private final Member author;

    @NonNull
    @Getter
    private final Question question;

    @NonNull
    @Getter
    @Setter
    private List<Photo> photos;

    @NonNull
    @Getter
    @Setter
    private List<Comment> comments;

    @NonNull
    @Getter
    @Setter
    private List<Notification> notifications;

    public void incrementFlagCount() {
        this.flagCount++;
    }
}
