package com.ra.course.com.stackoverflow.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    Long id;

    @NotNull
    @NotBlank(message = "{commentDto.text.blank}")
    String text;

    @PastOrPresent(message = "{date.future}")
    LocalDateTime creationDate;

    int voteCount;

    Long authorId;
    Long answerId;
    Long questionId;
}
