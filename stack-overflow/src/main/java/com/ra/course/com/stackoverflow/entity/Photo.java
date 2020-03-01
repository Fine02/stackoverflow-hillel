package com.ra.course.com.stackoverflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;
import com.ra.course.com.stackoverflow.entity.interfaces.Commentable;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Photo {

    @EqualsAndHashCode.Include
    private final long Id;

    @NonNull
    private String photoPath;

    @NonNull
    private LocalDateTime creationDate;

    @NonNull
    private Commentable commentable;
}
