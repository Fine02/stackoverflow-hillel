package com.ra.course.com.stackoverflow.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto implements Serializable {

    private static final long serialVersionUID = 99L;

    Long id;

    @NotNull
    @NotBlank(message = "{answerDto.text.blank}")
    String answerText;

    boolean accepted;

    int voteCount;

    int flagCount;

    @PastOrPresent(message = "{date.future}")
    LocalDateTime creationDate;

    Long authorId;

    Long questionId;

    List<CommentDto> comments;
}
