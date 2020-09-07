package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.bounty.BountyDto;
import com.ra.course.com.stackoverflow.dto.bounty.CreateBountyDto;
import com.ra.course.com.stackoverflow.entity.Bounty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BountyMapper {

    BountyMapper MAPPER = Mappers.getMapper(BountyMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "questionId", ignore = true)
    Bounty toBounty(CreateBountyDto createBountyDto);

    BountyDto toBountyDto(Bounty bounty);
}
