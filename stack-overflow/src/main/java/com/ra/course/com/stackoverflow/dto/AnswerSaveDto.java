package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.implementations.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class AnswerSaveDto implements GeneralSaveDto {

    private String answerText;
    private boolean accepted;
    private int voteCount;
    private int flagCount;
    private LocalDateTime creationDate;
    private Member author;
    private Question question;
    private List<Photo> photos;
    private List<Comment> comments;
    private List<Notification> notifications;
}
