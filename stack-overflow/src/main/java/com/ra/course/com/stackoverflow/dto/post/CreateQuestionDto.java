package com.ra.course.com.stackoverflow.dto.post;

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
    private String text;

    private List<String> tags = new ArrayList<>();

}
