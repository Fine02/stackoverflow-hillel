package com.ra.course.com.stackoverflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;

    private String text;

    private boolean read;

    private LocalDateTime creationTime;
}
