package com.ra.course.com.stackoverflow.dto.bounty;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BountyDto {

    private Long id;

    private int reputation;

    private LocalDateTime expiry;

    private Long creatorId;
}
