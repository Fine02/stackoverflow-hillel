package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Photo {

    @EqualsAndHashCode.Include
    private int photoId;

    @NonNull
    private String photoPath;

    @NonNull
    private LocalDateTime creationDate;

}
