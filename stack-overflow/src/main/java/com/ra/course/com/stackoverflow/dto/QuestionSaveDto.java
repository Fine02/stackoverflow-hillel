package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.implementations.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class QuestionSaveDto implements GeneralSaveDto{

    private String title;
    private String description;
    private int viewCount;
    private int voteCount;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
    private QuestionStatus status;
    private QuestionClosingRemark closingRemark;
    private Member author;
    private List<Comment> commentList;
    private List<Answer> answerList;
    private List<Bounty> bountyList;
    private List<Photo> photoList;
    private List<Tag> tagList;
}
