package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String description;

}
