package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper{

    TagMapper MAPPER = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "description", ignore = true )
    @Mapping(target = "id", ignore = true )
    Tag toTag(TagDto dto);

    TagDto toTagDto(Tag tag);

    List<Tag> toTag(List<TagDto> dto);

    List<TagDto> toTagDto(List<Tag> tag);
}
