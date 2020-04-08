package com.ra.course.com.stackoverflow.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Comment {

    private final Long id;

    @NonNull
    private String text;
    @EqualsAndHashCode.Exclude
    private final LocalDateTime creationDate;
    private int voteCount;
    @NonNull
    private final Long authorId;
    private final Long answerId;
    private final Long questionId;

}
