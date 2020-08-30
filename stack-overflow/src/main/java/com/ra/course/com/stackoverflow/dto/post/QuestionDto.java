package com.ra.course.com.stackoverflow.dto.post;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class QuestionDto {

    private Long id;

    private String title;

    private String text;

    private LocalDateTime updateTime = LocalDateTime.now();

    private QuestionStatus status = QuestionStatus.OPEN;

    private Long author;

    private List<TagDto> tags = new ArrayList<>();

}
