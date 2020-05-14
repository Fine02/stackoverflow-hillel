package com.ra.course.com.stackoverflow.dto.mapper.impl;

import com.ra.course.com.stackoverflow.dto.CommentDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Comment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentMapper implements Mapper<Comment, CommentDto> {
    @Override
    public Comment entityFromDto(CommentDto dto) {
        return Comment.builder()
                .id(dto.getId())
                .text(dto.getText())
                .creationDate(checkIfNull(dto.getCreationDate()))
                .voteCount(dto.getVoteCount())
                .authorId(dto.getAuthorId())
                .answerId(dto.getAnswerId())
                .questionId(dto.getQuestionId()).build();
    }

    @Override
    public CommentDto dtoFromEntity(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .text(entity.getText())
                .creationDate(checkIfNull(entity.getCreationDate()))
                .voteCount(entity.getVoteCount())
                .authorId(entity.getAuthorId())
                .answerId(entity.getAnswerId())
                .questionId(entity.getQuestionId()).build();
    }

    @Override
    public List<Comment> entityFromDto(List<CommentDto> dtos) {
        return checkIfNull(dtos).stream()
                .map(this::entityFromDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> dtoFromEntity(List<Comment> entities) {
        return checkIfNull(entities).stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    private LocalDateTime checkIfNull(LocalDateTime dateTime) {
        return Objects.nonNull(dateTime) ? dateTime
                : LocalDateTime.now();
    }

    private <T> List <T> checkIfNull(List<T> list){
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }
}
