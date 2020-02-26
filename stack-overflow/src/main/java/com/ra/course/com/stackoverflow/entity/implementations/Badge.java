package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;


@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Badge {

    @EqualsAndHashCode.Include
    private final long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

}
