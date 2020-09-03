package com.ra.course.com.stackoverflow.dto.post;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AnswerDto {

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String text;

    @EqualsAndHashCode.Include
    private boolean accepted;

    private int voteCount;

    private LocalDateTime creationTime = LocalDateTime.now();

    @EqualsAndHashCode.Include
    private Long author;

    @EqualsAndHashCode.Include
    private Long question;

    private List<CommentDto> comments = new ArrayList<>();
}
