package com.ra.course.com.stackoverflow.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LogInDto {
    private String name;
    private Long id;
    private String password;
    private String email;
}
