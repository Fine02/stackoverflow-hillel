package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.*;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question {

    @EqualsAndHashCode.Include
    final private Long id;

    private String title;

    @Builder.Default
    private String description = "";

    @Builder.Default
    private int viewCount = 0;

    @Builder.Default
    private int voteCount = 0;

    @Builder.Default
    private LocalDateTime creationTime = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updateTime = LocalDateTime.now();

    @Builder.Default
    private QuestionStatus status = QuestionStatus.OPEN;

    @Builder.Default
    private QuestionClosingRemark closingRemark = QuestionClosingRemark.NOT_MARKED_FOR_CLOSING;

    @ToString.Exclude
//    private Member author;
    private Long authorId;

    @Builder.Default
    private Optional<Bounty> bounty = Optional.empty();

    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @Builder.Default
    private List<Answer> answerList = new ArrayList<>();
    
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    @Builder.Default
    private List<Tag> tagList = new ArrayList<>();

    @Builder.Default
    private Map<Long, QuestionClosingRemark> membersIdsWhoVotedQuestionToClose = new HashMap<>();

}
