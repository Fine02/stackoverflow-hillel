package com.ra.course.com.stackoverflow.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotificationDto {

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String text;

    @EqualsAndHashCode.Include
    private boolean read;

    private LocalDateTime creationTime;
}
