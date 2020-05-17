package com.ra.course.com.stackoverflow.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {

    @NotNull
    @Size(min = 5, max = 20, message = "{registerDto.name.size}")
    String name;


    @NotNull
    @Email(message = "{registerDto.email.invalid}")
    String email;

    @NotNull
    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})[\\s\\S]{8,}$",
            message = "{registerDto.password.invalid}")
    String password;
}
