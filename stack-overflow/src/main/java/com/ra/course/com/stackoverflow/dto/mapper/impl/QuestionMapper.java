package com.ra.course.com.stackoverflow.dto.mapper.impl;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionMapper implements Mapper<Question, QuestionDto> {

    private final transient AnswerMapper answerMapper;
    private final transient CommentMapper commentMapper;

    @Override
    public Question entityFromDto(QuestionDto dto) {
        return Question.builder().id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .viewCount(dto.getViewCount())
                .voteCount(dto.getVoteCount())
                .creationTime(checkIfNull(dto.getCreationTime()))
                .updateTime(checkIfNull(dto.getUpdateTime()))
                .status(Objects.nonNull(dto.getStatus()) ? dto.getStatus() : QuestionStatus.OPEN)
                .closingRemark(Objects.nonNull(dto.getClosingRemark()) ? dto.getClosingRemark() : QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                .authorId(dto.getAuthorId())
                .commentList(commentMapper.entityFromDto(dto.getCommentList()))
                .answerList(answerMapper.entityFromDto(dto.getAnswerList()))
                .photoList(checkIfNull(dto.getPhotoList()))
                .tagList(checkIfNull(dto.getTagList()))
                .build();
    }

    @Override
    public QuestionDto dtoFromEntity(Question entity) {
        
        return QuestionDto.builder().id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .viewCount(entity.getViewCount())
                .voteCount(entity.getVoteCount())
                .creationTime(checkIfNull(entity.getCreationTime()))
                .updateTime(checkIfNull(entity.getUpdateTime()))
                .status(Objects.nonNull(entity.getStatus()) ? entity.getStatus() : QuestionStatus.OPEN)
                .closingRemark(Objects.nonNull(entity.getClosingRemark()) ? entity.getClosingRemark() : QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                .authorId(entity.getAuthorId())
                .commentList(commentMapper.dtoFromEntity(entity.getCommentList()))
                .answerList(answerMapper.dtoFromEntity(entity.getAnswerList()))
                .photoList(checkIfNull(entity.getPhotoList()))
                .tagList(checkIfNull(entity.getTagList()))
                .build();
    }

    public List<QuestionDto> dtoFromEntity (List<Question> entities){
        return checkIfNull(entities).stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }
    public List<Question> entityFromDto (List<QuestionDto> dtos){
        return checkIfNull(dtos).stream()
                .map(this::entityFromDto)
                .collect(Collectors.toList());
    }
    
    private LocalDateTime checkIfNull(LocalDateTime dateTime){
        return Objects.nonNull(dateTime) ? dateTime
                : LocalDateTime.now();
    }
    private <T> List<T> checkIfNull(List<T> list){
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }
}
