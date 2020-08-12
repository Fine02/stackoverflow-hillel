package com.ra.course.com.stackoverflow.dto.bounty;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
public class CreateBountyDto {

    @Min(1)
    private int reputation;

    @Future
    private LocalDateTime expiry;

}
