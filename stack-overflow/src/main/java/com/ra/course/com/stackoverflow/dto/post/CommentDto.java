package com.ra.course.com.stackoverflow.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CommentDto {

    private Long id;

    @EqualsAndHashCode.Include
    private String text;

    private LocalDateTime creationTime = LocalDateTime.now();

    private int voteCount;

    @EqualsAndHashCode.Include
    private Long author;

    @EqualsAndHashCode.Include
    private Long answerId;

    @EqualsAndHashCode.Include
    private Long questionId;
}
