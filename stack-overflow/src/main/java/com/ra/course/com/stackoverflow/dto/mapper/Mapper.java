package com.ra.course.com.stackoverflow.dto.mapper;

import java.util.List;

public interface Mapper<Entity, Dto> {

    Entity entityFromDto(Dto dto);

    Dto dtoFromEntity (Entity entity);

    List<Entity> entityFromDto(List<Dto> dtos);

    List<Dto> dtoFromEntity(List<Entity> entities);
}
