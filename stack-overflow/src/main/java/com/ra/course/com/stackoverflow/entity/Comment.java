package com.ra.course.com.stackoverflow.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Comment {

    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String text;

    private LocalDateTime creationTime = LocalDateTime.now();

    private int voteCount;

    @EqualsAndHashCode.Include
    private Long author;

    @EqualsAndHashCode.Include
    private Long answer;

    @EqualsAndHashCode.Include
    private Long question;

}
