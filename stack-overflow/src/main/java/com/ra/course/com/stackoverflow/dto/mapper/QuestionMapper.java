package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper MAPPER = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "tags", expression = "java(TagMapper.MAPPER.toTagDto(question.getTags()))")
    QuestionDto toQuestionDto(Question question);

    @Mapping(target = "tags", expression = "java(TagMapper.MAPPER.toTagDto(question.getTags()))")
    @Mapping(target = "answers", expression = "java(AnswerMapper.MAPPER.toAnswerDto(question.getAnswers()))")
    @Mapping(target = "comments", expression = "java(CommentMapper.MAPPER.toCommentDto(question.getComments()))")
//    @Mapping(target = "bounty", expression = "java(BountyMapper.MAPPER.toBountyDto(question.getBounty()))")
    QuestionFullDto toQuestionFullDto(Question question);

    List<QuestionDto> toQuestionDto(List<Question> question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "voteCount", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "closingRemark", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "bounty", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "membersIdsWhoVotedQuestionToClose", ignore = true)
    Question toQuestion(CreateQuestionDto createDto);
}
