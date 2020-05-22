package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto implements Serializable {

    private static final long serialVersionUID = 99L;

    Long id;

    String name;

    String email;

    AccountStatus status;

    int reputation;

    List<QuestionDto> questions;

    List<AnswerDto> answers;

    List<CommentDto> comments;

    List<Long> upVotedQuestionsId;

    List<Long> upVotedAnswersId;

    List<Long> upVotedCommentsId;

    List<Long> downVotedQuestionsId;

    List<Long> downVotedAnswersId;

    List<Long> downVotedCommentsId;
}
