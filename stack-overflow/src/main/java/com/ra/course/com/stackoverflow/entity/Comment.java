package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.interfaces.Commentable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {

    @EqualsAndHashCode.Include
    private final long id;

    @Builder.Default
    @NonNull
    private String text = "";

    @Builder.Default
    @NonNull
    private LocalDateTime creationDate = LocalDateTime.now();

    private int voteCount;

    private int flagCount;

    @NonNull
    private final Member author;

    @NonNull
    private Commentable commentable;


    public void incrementVoteCount() {
        this.voteCount++;
    }

}
