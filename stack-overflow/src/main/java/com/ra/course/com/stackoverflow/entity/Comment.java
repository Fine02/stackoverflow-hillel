package com.ra.course.com.stackoverflow.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@EqualsAndHashCode
public class Comment {

    private long id;

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
