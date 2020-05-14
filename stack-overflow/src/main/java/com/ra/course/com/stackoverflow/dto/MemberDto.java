package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.enums.Badge;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {

    Long id;

    @NotNull
    @Size(min = 5, max = 20, message = "{memberDto.name.size}")
    String name;


    @NotNull
    @Email(message = "{memberDto.email.invalid}")
    String email;

    @NotNull
    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$",
             message = "{memberDto.password.invalid}")
    String password;

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
