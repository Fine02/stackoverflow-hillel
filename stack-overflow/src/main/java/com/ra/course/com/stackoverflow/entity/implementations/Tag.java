package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    @EqualsAndHashCode.Include
    private final long id;

    @NonNull
    private final String name;

    @NonNull
    private String description;

    @NonNull
    private List<Question> questionList;

    private int dailyAskedFrequency;

    private int weeklyAskedFrequency;
}
