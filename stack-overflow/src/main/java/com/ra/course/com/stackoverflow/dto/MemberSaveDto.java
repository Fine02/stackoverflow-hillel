package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.implementations.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class MemberSaveDto implements GeneralSaveDto {

    private Account account;
    private List<Badge> badges;
    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;
    private List<Notification> notifications;

}
