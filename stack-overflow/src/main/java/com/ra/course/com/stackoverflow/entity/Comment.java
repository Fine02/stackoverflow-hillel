package com.ra.course.com.stackoverflow.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment<T> {

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
    private T commentable;


    public void incrementVoteCount() {
        this.voteCount++;
    }

}
