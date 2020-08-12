package com.ra.course.com.stackoverflow.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of="text")
@EqualsAndHashCode(of = {"text", "author", "question"})
public class Answer {

    private Long id;

    private String text;

    private boolean accepted;

    private int voteCount;

    private int flagCount;

    private LocalDateTime creationTime = LocalDateTime.now();

    private Long author;

    private Long question;

    private List<Photo> photos = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

}
