package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bounty {

    @EqualsAndHashCode.Include
    private long id;

    private int reputation;

    @NonNull
    private LocalDateTime expiry;

    @NonNull
    private final Long creator_id;

}
