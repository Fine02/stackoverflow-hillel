package com.ra.course.com.stackoverflow.entity.implementations;

import com.ra.course.com.stackoverflow.entity.interfaces.Searchable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
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

    @NonNull
    private Searchable question;

    @NonNull
    private Answer answer;

}
