package com.ra.course.com.stackoverflow.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "photoPath")
@EqualsAndHashCode(of = "photoPath")
public class Photo {

    private Long id;

    private String photoPath;

    private LocalDateTime creationDate;
    private Long answerId;
    private Long questionId;
}
