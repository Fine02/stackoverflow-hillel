package com.ra.course.com.stackoverflow.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {

    @Size(min = 5, max = 20, message = "{userDto.name.size}")
    private String name;

    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$",
            message = "{userDto.password.invalid}")
    private String password;
}
