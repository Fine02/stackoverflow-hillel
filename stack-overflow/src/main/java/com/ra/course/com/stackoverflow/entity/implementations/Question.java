package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Question implements Searchable {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private int viewCount;

    private int voteCount;

    @NonNull
    private LocalDateTime creationTime;

    @NonNull
    private LocalDateTime updateTime;

    @NonNull
    private QuestionStatus status;

    @NonNull
    private QuestionClosingRemark closingRemark;

    @NonNull
    private Member author;

    @NonNull
    private List<Comment> commentList;

    @NonNull
    private List<Answer> answerList;

    @NonNull
    private List<Bounty> bountyList;

    @NonNull
    private List<Photo> photoList;

    @NonNull
    private List<Tag> tagList;
}
