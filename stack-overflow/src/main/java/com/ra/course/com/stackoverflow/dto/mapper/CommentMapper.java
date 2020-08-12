package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.post.CommentDto;
import com.ra.course.com.stackoverflow.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "questionId", source = "question")
    @Mapping(target = "answerId", source = "answer")
    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDto(List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "voteCount", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "answer", ignore = true)
    Comment toComment(CreateDto createDto);

}
