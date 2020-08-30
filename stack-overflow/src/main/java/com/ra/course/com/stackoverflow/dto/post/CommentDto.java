package com.ra.course.com.stackoverflow.dto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;

    private String text;

    private LocalDateTime creationTime = LocalDateTime.now();

    private int voteCount;

    private Long author;

    private Long answerId;
    private Long questionId;
}
