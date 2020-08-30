package com.ra.course.com.stackoverflow.dto.post;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AnswerDto {

    private Long id;

    private String text;

    private boolean accepted;

    private int voteCount;

    private LocalDateTime creationTime = LocalDateTime.now();

    private Long author;

    private Long question;

    private List<CommentDto> comments = new ArrayList<>();
}
