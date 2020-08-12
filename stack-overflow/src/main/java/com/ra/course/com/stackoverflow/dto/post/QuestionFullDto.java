package com.ra.course.com.stackoverflow.dto.post;

import com.ra.course.com.stackoverflow.dto.bounty.BountyDto;
import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class QuestionFullDto {

    private Long id;

    private String title;

    private String text;

    private int viewCount;

    private int voteCount;

    @EqualsAndHashCode.Exclude
    private LocalDateTime creationTime = LocalDateTime.MIN;

    @EqualsAndHashCode.Exclude
    private LocalDateTime updateTime = LocalDateTime.MIN;

    private QuestionStatus status = QuestionStatus.OPEN;

    private Long author;

    private BountyDto bounty;

    private List<CommentDto> comments = new ArrayList<>();

    private List<AnswerDto> answers = new ArrayList<>();

    private List<TagDto> tags = new ArrayList<>();
}
