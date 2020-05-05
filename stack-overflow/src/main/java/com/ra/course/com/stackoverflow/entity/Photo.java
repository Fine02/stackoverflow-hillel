package com.ra.course.com.stackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Photo {

    @EqualsAndHashCode.Include
    private final long id;

    @NonNull
    private String photoPath;

    @NonNull
    private LocalDateTime creationDate;
    private final Long answerId;
    private final Long questionId;
}
