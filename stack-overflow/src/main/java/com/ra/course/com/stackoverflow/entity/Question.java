package com.ra.course.com.stackoverflow.entity;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question {

    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String title;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String text;

    private int viewCount;

    private int voteCount;

    private LocalDateTime creationTime = LocalDateTime.now();

    private LocalDateTime updateTime = LocalDateTime.now();

    @EqualsAndHashCode.Include
    private QuestionStatus status = QuestionStatus.OPEN;

    private QuestionClosingRemark closingRemark = QuestionClosingRemark.NOT_MARKED_FOR_CLOSING;

    @EqualsAndHashCode.Include
    private Long author;

    private Bounty bounty;

    private List<Comment> comments = new ArrayList<>();

    private List<Answer> answers = new ArrayList<>();
    
    private List<Photo> photos = new ArrayList<>();

    private List<Tag> tags = new ArrayList<>();

    private Map<Long, QuestionClosingRemark> membersIdsWhoVotedQuestionToClose = new HashMap<>();

}
