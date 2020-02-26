package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Tag {

    @EqualsAndHashCode.Include
    final private long id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private int dailyAskedFrequency;
    private int weeklyAskedFrequency;

}
