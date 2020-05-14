package com.ra.course.com.stackoverflow.dto;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Photo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {

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

    List<Photo> photos;

    List<CommentDto> comments;
}
