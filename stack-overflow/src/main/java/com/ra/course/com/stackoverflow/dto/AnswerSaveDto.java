package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.implementations.Comment;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Notification;
import com.ra.course.com.stackoverflow.entity.implementations.Photo;
import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
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
    private Searchable question;
    private List<Photo> photos;
    private List<Comment> comments;
    private List<Notification> notifications;
}
