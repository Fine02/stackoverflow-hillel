package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bounty {

    private Long id;

    @EqualsAndHashCode.Include
    @ToString.Include
    private int reputation;

    @ToString.Include
    private LocalDateTime expiry;

    @EqualsAndHashCode.Include
    private Long creatorId;

    @EqualsAndHashCode.Include
    private Long questionId;

}
