package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {

    Long id;

    String name;

    String email;

    AccountStatus status;

    int reputation;

    @Builder.Default
    Map<Badge, Set<Question>> questionBadges = new HashMap<>();

    List<QuestionDto> questions;

    List<AnswerDto> answers;

    List<CommentDto> comments;

    List<Notification> notifications;

    List<Long> upVotedQuestionsId;

    List<Long> upVotedAnswersId;

    List<Long> upVotedCommentsId;

    List<Long> downVotedQuestionsId;

    List<Long> downVotedAnswersId;

    List<Long> downVotedCommentsId;
}
