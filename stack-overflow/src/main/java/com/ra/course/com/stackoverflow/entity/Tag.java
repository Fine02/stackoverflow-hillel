package com.ra.course.com.stackoverflow.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String name;

    private String description;

}
