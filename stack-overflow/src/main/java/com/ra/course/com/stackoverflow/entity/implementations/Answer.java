package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Answer implements IDEntity {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private String answerText;

    private boolean accepted;

    private int voteCount;

    private int flagCount;

    @NonNull
    private final LocalDateTime creationDate;

    @NonNull
    private final Member author;

    @NonNull
    private final Searchable question;

    @NonNull
    private List<Photo> photos;

    @NonNull
    private List<Comment> comments;

    @NonNull
    private List<Notification> notifications;

    public void incrementFlagCount() {
        this.flagCount++;
    }
}
