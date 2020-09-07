package com.ra.course.com.stackoverflow.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateQuestionDto {

    @NotBlank
    private String title;

    @NotBlank
    private String text;
}
