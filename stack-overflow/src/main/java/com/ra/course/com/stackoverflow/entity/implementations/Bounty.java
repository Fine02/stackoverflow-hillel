package com.ra.course.com.stackoverflow.entity.implementations;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Bounty {

    @EqualsAndHashCode.Include
    private final long id;

    private int reputation;

    @NonNull
    private LocalDateTime expiry;

    @NonNull
    private final Member creator;

}
