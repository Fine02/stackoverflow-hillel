package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {

    @EqualsAndHashCode.Include
    final private LocalDateTime createdOn;

    @NonNull
    private String content;

    public Notification(String content) {
        Objects.requireNonNull(content, "'content' argument must no be null");

        this.content = content;
        this.createdOn = LocalDateTime.now();
    }
}
