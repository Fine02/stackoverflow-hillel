package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Notification {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    final private LocalDateTime createdON;

    @NonNull
    private String content;

    @NonNull
    private final List<Notification> notifications;

}
