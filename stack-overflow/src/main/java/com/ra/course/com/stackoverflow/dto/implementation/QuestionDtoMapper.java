package com.ra.course.com.stackoverflow.dto.implementation;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.entity.implementation.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper()
public interface QuestionDtoMapper {
    @Mappings({
            @Mapping(target = "id", source = "0"), //TODO: id must be taken from data base!
            @Mapping(target = "title", source = "questionDto.title"),
            @Mapping(target = "description", source = "questionDto.description"),
            @Mapping(target = "viewCount", source = "questionDto.viewCount"),
            @Mapping(target = "voteCount", source = "questionDto.voteCount"),
            @Mapping(target = "creationTime", source = "questionDto.creationTime"),
            @Mapping(target = "updateTime", source = "questionDto.updateTime"),
            @Mapping(target = "status", source = "questionDto.status"),
            @Mapping(target = "closingRemark", source = "questionDto.closingRemark"),
            @Mapping(target = "author", source = "questionDto.author"),
            @Mapping(target = "bounty", source = "questionDto.bounty"),
            @Mapping(target = "commentList", source = "questionDto.commentList"),
            @Mapping(target = "answerList", source = "questionDto.answerList"),
            @Mapping(target = "photoList", source = "questionDto.photoList")
    })
    Question toEntity(QuestionDto questionDto);

    @Mappings({
            @Mapping(target = "title", source = "question.title"),
            @Mapping(target = "description", source = "question.description"),
            @Mapping(target = "viewCount", source = "question.viewCount"),
            @Mapping(target = "voteCount", source = "question.voteCount"),
            @Mapping(target = "creationTime", source = "question.creationTime"),
            @Mapping(target = "updateTime", source = "question.updateTime"),
            @Mapping(target = "status", source = "question.status"),
            @Mapping(target = "closingRemark", source = "question.closingRemark"),
            @Mapping(target = "author", source = "question.author"),
            @Mapping(target = "bounty", source = "question.bounty"),
            @Mapping(target = "commentList", source = "question.commentList"),
            @Mapping(target = "answerList", source = "question.answerList"),
            @Mapping(target = "photoList", source = "question.photoList")
    })
    QuestionDto toDto(Question question);
}
