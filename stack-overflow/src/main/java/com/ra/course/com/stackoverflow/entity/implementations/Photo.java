package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Photo {

    @EqualsAndHashCode.Include
    private final long photoId;

    @NonNull
    private String photoPath;

    @NonNull
    private LocalDateTime creationDate;

    @NonNull
    private Searchable question;

    @NonNull
    private Answer answer;

}
