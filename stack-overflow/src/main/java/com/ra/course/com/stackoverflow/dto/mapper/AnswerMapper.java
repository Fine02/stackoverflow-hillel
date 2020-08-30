package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.post.AnswerDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper MAPPER = Mappers.getMapper(AnswerMapper.class);

    @Mapping(target = "comments", expression = "java(CommentMapper.MAPPER.toCommentDto(answer.getComments()))")
    AnswerDto toAnswerDto(Answer answer);

    List<AnswerDto> toAnswerDto(List<Answer> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "accepted", ignore = true)
    @Mapping(target = "voteCount", ignore = true)
    @Mapping(target = "flagCount", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "text", source = "text")
    Answer toAnswer(CreateDto createDto);
}
