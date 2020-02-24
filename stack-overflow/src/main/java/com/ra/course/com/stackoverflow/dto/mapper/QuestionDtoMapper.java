package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.entity.Searchable;
import com.ra.course.com.stackoverflow.entity.implementation.Question;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper()
public interface QuestionDtoMapper {
    @Mappings({
            @Mapping(target = "id", source = "nextId"),
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
            @Mapping(target = "photoList", source = "questionDto.photoList"),
            @Mapping(target = "tagList", source = "questionDto.tagList")
    })
    Searchable toEntity(final QuestionDto questionDto);

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
            @Mapping(target = "photoList", source = "question.photoList"),
            @Mapping(target = "tagList", source = "question.tagList")
    })
    QuestionDto toDto(final Searchable question);

    @Named("nextId")
    default long getNextId(final GeneralRepository<Searchable> questionRepository) throws RepositoryException {
        return questionRepository.getNextId();
    }
}
