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

    @NonNull
    final private LocalDateTime createdOn;

    @NonNull
    private String content;

    @NonNull
    private List<Notification> notifications;

}
