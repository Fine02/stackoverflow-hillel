package com.ra.course.com.stackoverflow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateQuestionDto {

    @NotNull
    @NotBlank(message = "{questionDto.title.blank}")
    private String title;

    @NotNull
    @NotBlank(message = "{questionDto.description.blank}")
    private String description;

    private List<Integer> tags = new ArrayList<>();

}
