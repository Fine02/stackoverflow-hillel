package com.ra.course.com.stackoverflow.dto.mapper.impl;

import com.ra.course.com.stackoverflow.dto.AnswerDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AnswerMapper implements Mapper<Answer, AnswerDto> {

    private final transient CommentMapper commentMapper;

    @Override
    public Answer entityFromDto(AnswerDto dto) {
        return Answer.builder().id(dto.getId())
                .answerText(dto.getAnswerText())
                .accepted(dto.isAccepted())
                .voteCount(dto.getVoteCount())
                .flagCount(dto.getFlagCount())
                .creationDate(checkIfNull(dto.getCreationDate()))
                .authorId(dto.getAuthorId())
                .questionId(dto.getQuestionId())
                .comments(commentMapper.entityFromDto(dto.getComments())).build();
    }

    @Override
    public AnswerDto dtoFromEntity(Answer entity) {
        return AnswerDto.builder().id(entity.getId())
                .answerText(entity.getAnswerText())
                .accepted(entity.isAccepted())
                .voteCount(entity.getVoteCount())
                .flagCount(entity.getFlagCount())
                .creationDate(checkIfNull(entity.getCreationDate()))
                .authorId(entity.getAuthorId())
                .questionId(entity.getQuestionId())
                .comments(commentMapper.dtoFromEntity(entity.getComments())).build();
    }

    @Override
    public List<AnswerDto> dtoFromEntity(List<Answer> entities) {
        return checkIfNull(entities).stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Answer> entityFromDto(List<AnswerDto> dtos) {
        return checkIfNull(dtos).stream()
                .map(this::entityFromDto)
                .collect(Collectors.toList());
    }

    private LocalDateTime checkIfNull(LocalDateTime dateTime) {
        return Objects.nonNull(dateTime) ? dateTime
                : LocalDateTime.now();
    }

    private <T> List<T> checkIfNull(List<T> list) {
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }

}
