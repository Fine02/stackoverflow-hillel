package com.ra.course.com.stackoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInDto {

    @NotBlank(message = "{logInDto.password.blank}")
    private String email;

    @NotBlank(message = "{logInDto.email.blank}")
    private String password;
}
