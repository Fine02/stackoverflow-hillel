package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Comment {

    @EqualsAndHashCode.Include
    @Getter
    private final long id;

    @NonNull
    @Getter
    @Setter
    private String text;

    @NonNull
    @Getter
    private LocalDateTime creationDate;

    @Getter
    @Setter
    private int voteCount;

    @Getter
    private int flagCount;

    @NonNull
    @Getter
    private final Member author;

    public void incrementVoteCount() {
        this.voteCount++;
    }

}
