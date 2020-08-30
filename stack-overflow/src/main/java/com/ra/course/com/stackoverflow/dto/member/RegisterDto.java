package com.ra.course.com.stackoverflow.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDto {

    @NotNull
    @Size(min = 5, max = 20, message = "{userDto.name.size}")
    private String name;


    @NotNull
    @Email(message = "{userDto.email.invalid}")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$",
            message = "{userDto.password.invalid}")
    private String password;
}
