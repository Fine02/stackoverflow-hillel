package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of={"text", "member", "question"})
@ToString(of="text")
public class Notification {

    private Long id;

    private LocalDateTime creationTime = LocalDateTime.now();

    private String text;

    private boolean read;

    private Long question;
    private Long answer;
    private Long comment;

    private Long member;
}
