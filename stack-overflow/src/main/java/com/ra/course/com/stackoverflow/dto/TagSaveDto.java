package com.ra.course.com.stackoverflow.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TagSaveDto implements GeneralSaveDto {
    private String name;
    private String description;
    private int dailyAskedFrequency;
    private int weeklyAskedFrequency;
}
