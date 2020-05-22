package com.ra.course.com.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {

    private Long id;

    @Size(min = 5, max = 20, message = "{registerDto.name.size}")
    private String name;

    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$",
            message = "{registerDto.password.invalid}")
    private String password;
}
