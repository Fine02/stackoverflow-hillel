package com.ra.course.com.stackoverflow.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDto implements Serializable {

    private static final long serialVersionUID = 99L;

    Long id;

    @NotBlank(message = "{tagDto.name.blank}")
    String name;

    @NotBlank(message = "{tagDto.description.blank}")
    String description;
}
