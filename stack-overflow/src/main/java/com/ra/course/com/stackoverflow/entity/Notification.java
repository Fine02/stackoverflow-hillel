package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {

    @EqualsAndHashCode.Include
    final private long id;

    @Builder.Default
    @NonNull
    final private LocalDateTime createdOn = LocalDateTime.now();

    @NonNull
    private String content;

    @NonNull
    private List<Notification> notifications;

}
