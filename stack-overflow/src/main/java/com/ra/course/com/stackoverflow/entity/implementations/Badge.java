package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Badge {

    @EqualsAndHashCode.Include
    private final long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

}
