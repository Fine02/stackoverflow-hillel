package com.ra.course.com.stackoverflow.dto.post;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class QuestionDto {

    private Long id;

    @EqualsAndHashCode.Include
    private String title;

    @EqualsAndHashCode.Include
    private String text;

    private LocalDateTime updateTime = LocalDateTime.now();

    @EqualsAndHashCode.Include
    private QuestionStatus status = QuestionStatus.OPEN;

    @EqualsAndHashCode.Include
    private Long author;

    private List<TagDto> tags = new ArrayList<>();

}
