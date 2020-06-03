package com.ra.course.com.stackoverflow.dto.mapper.impl;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TagMapper implements Mapper<Tag, TagDto> {

    @Override
    public Tag entityFromDto(TagDto dto) {
        return new Tag(dto.getId(), dto.getName(), dto.getDescription());
    }

    @Override
    public TagDto dtoFromEntity(Tag entity) {
        return new TagDto(entity.getId(), entity.getName(), entity.getDescription());
    }

    @Override
    public List<Tag> entityFromDto(List<TagDto> dtos) {
        return checkIfNull(dtos).stream()
                .map(this::entityFromDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> dtoFromEntity(List<Tag> entities) {
        return checkIfNull(entities).stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    private <T> List<T> checkIfNull(List<T> list){
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }
}
