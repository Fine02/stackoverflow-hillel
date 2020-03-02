package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.interfaces.Commentable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Answer implements Commentable {

    @EqualsAndHashCode.Include
    final private long id;

    @Builder.Default
    @NonNull
    private String answerText = " ";

    private boolean accepted;

    private int voteCount;

    private int flagCount;

    @Builder.Default
    @NonNull
    private final LocalDateTime creationDate = LocalDateTime.now();

    @NonNull
    private final Member author;

    @NonNull
    private final Question question;

    @Builder.Default
    @NonNull
    private List<Photo> photos = new ArrayList<>();

    @Builder.Default
    @NonNull
    private List<Comment> comments = new ArrayList<>();

    public void incrementFlagCount() {
        this.flagCount++;
    }
}
