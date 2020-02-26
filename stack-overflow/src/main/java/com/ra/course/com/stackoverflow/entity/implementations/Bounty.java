package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bounty {

    @EqualsAndHashCode.Include
    private final long id;

    private int reputation;

    @NonNull
    private LocalDateTime expiry;

    @NonNull
    private final Member creator;

}
