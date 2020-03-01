package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.interfaces.Commentable;
import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuperBuilder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question implements Searchable, Commentable {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private String title;

    @NonNull
    @Builder.Default
    private String description = "";

    @Builder.Default
    private int viewCount = 0;

    @Builder.Default
    private int voteCount = 0;

    @NonNull
    @Builder.Default
    private LocalDateTime creationTime = LocalDateTime.now();

    @NonNull
    @Builder.Default
    private LocalDateTime updateTime = LocalDateTime.now();

    @NonNull
    @Builder.Default
    private QuestionStatus status = QuestionStatus.OPEN;

    @NonNull
    @Builder.Default
    private QuestionClosingRemark closingRemark = QuestionClosingRemark.NOT_MARKED_FOR_CLOSING;

    @NonNull
    private Member author;

    @NonNull
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @NonNull
    @Builder.Default
    private List<Answer> answerList = new ArrayList<>();

    @Builder.Default
    private Optional<Bounty> bounty = Optional.empty();

    @NonNull
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    @NonNull
    @Builder.Default
    private List<Tag> tagList = new ArrayList<>();
}
