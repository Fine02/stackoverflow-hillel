package com.ra.course.com.stackoverflow.dto.post;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDto {

    @NotBlank(message = "{dto.text.blank}")
    private String text;

}
