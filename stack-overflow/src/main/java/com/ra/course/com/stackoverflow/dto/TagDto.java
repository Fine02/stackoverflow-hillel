package com.ra.course.com.stackoverflow.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TagDto implements Serializable {

    private static final long serialVersionUID = 88L;


    @NotBlank(message = "{tagDto.name.blank}")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
