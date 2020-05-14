package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDto {

    Long id;

    @NotNull
    @NotBlank(message = "{questionDto.title.blank}")
    String title;

    @NotNull
    @NotBlank(message = "{questionDto.description.blank}")
    String description;

    int viewCount;

    int voteCount;

    @PastOrPresent(message = "{date.future}")
    LocalDateTime creationTime;

    @PastOrPresent(message = "{date.future}")
    LocalDateTime updateTime;

    QuestionStatus status;

    QuestionClosingRemark closingRemark;

    Long authorId;

    @Builder.Default
    Optional<Bounty> bounty = Optional.empty();

    List<CommentDto> commentList;

    List<AnswerDto> answerList;

    List<Photo> photoList;

    List<Tag> tagList;

    @Builder.Default
    Map<Long, QuestionClosingRemark> membersIdsWhoVotedQuestionToClose = new HashMap<>();

}
