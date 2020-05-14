package com.ra.course.com.stackoverflow.dto.mapper.impl;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.mapper.Mapper;
import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberMapper implements Mapper<Member, MemberDto> {

    private final transient QuestionMapper questionMapper;
    private final transient AnswerMapper answerMapper;
    private final transient CommentMapper commentMapper;

    @Override
    public Member entityFromDto(MemberDto dto) {
        var account = Account.builder().id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .status(Objects.nonNull(dto.getStatus())
                        ? dto.getStatus()
                        : AccountStatus.ACTIVE)
                .reputation(dto.getReputation()).build();
        return Member.builder().account(account)
                .questions(questionMapper.entityFromDto(dto.getQuestions()))
                .answers(answerMapper.entityFromDto(dto.getAnswers()))
                .comments(commentMapper.entityFromDto(dto.getComments()))
                .downVotedAnswersId(checkIfNull(dto.getDownVotedAnswersId()))
                .upVotedAnswersId(checkIfNull(dto.getUpVotedAnswersId()))
                .downVotedQuestionsId(checkIfNull(dto.getDownVotedQuestionsId()))
                .upVotedQuestionsId(checkIfNull(dto.getUpVotedQuestionsId()))
                .downVotedCommentsId(checkIfNull(dto.getDownVotedCommentsId()))
                .upVotedCommentsId(checkIfNull(dto.getUpVotedCommentsId()))
                .build();
    }

    @Override
    public MemberDto dtoFromEntity(Member entity) {
        return MemberDto.builder().id(entity.getAccount().getId())
                .name(entity.getAccount().getName())
                .email(entity.getAccount().getEmail())
                .password(entity.getAccount().getPassword())
                .status(entity.getAccount().getStatus())
                .reputation(entity.getAccount().getReputation())
                .questions(questionMapper.dtoFromEntity(entity.getQuestions()))
                .answers(answerMapper.dtoFromEntity(entity.getAnswers()))
                .comments(commentMapper.dtoFromEntity(entity.getComments()))
                .downVotedAnswersId(checkIfNull(entity.getDownVotedAnswersId()))
                .upVotedAnswersId(checkIfNull(entity.getUpVotedAnswersId()))
                .downVotedQuestionsId(checkIfNull(entity.getDownVotedQuestionsId()))
                .upVotedQuestionsId(checkIfNull(entity.getUpVotedQuestionsId()))
                .downVotedCommentsId(checkIfNull(entity.getDownVotedCommentsId()))
                .upVotedCommentsId(checkIfNull(entity.getUpVotedCommentsId()))
                .build();
    }

    @Override
    public List<MemberDto> dtoFromEntity (List<Member> entities){
        return checkIfNull(entities).stream()
                .map(this::dtoFromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public List<Member> entityFromDto (List<MemberDto> dtos){
        return checkIfNull(dtos).stream()
                .map(this::entityFromDto)
                .collect(Collectors.toList());
    }

    private <T> List <T> checkIfNull(List<T> list){
        return Objects.nonNull(list) ? list : new ArrayList<>();
    }

}
