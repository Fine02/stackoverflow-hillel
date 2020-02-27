package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.interfaces.Commentable;
import com.ra.course.com.stackoverflow.entity.interfaces.IDEntity;
import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question implements Searchable, Commentable, IDEntity {

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

    private Bounty bounty;

    @NonNull
    private List<Photo> photoList;

    @NonNull
    private List<Tag> tagList;
}
