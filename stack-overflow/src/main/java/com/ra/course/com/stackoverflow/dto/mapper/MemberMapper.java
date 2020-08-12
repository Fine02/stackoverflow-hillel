package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.dto.member.RegisterDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "name", source = "account.name")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "role", source = "account.role")
    @Mapping(target = "status", source = "account.status")
    @Mapping(target = "reputation", source = "account.reputation")
    @Mapping(target = "questions", expression = "java(member.getQuestions().size())")
    @Mapping(target = "answers", expression = "java(member.getAnswers().size())")
    @Mapping(target = "comments", expression = "java(member.getComments().size())")
    MemberDto toMemberDto(Member member);

    List<MemberDto> toMemberDto(List<Member> members);

    @Mapping(target = "account.password", source = "password")
    @Mapping(target = "account.name", source = "name")
    @Mapping(target = "account.email", source = "email")
    @Mapping(target = "account.id", ignore = true)
    @Mapping(target = "account.role", ignore = true)
    @Mapping(target = "account.status", ignore = true)
    @Mapping(target = "account.reputation", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "answers", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "questionBadges", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "upVotedQuestionsId", ignore = true)
    @Mapping(target = "downVotedQuestionsId", ignore = true)
    @Mapping(target = "upVotedAnswersId", ignore = true)
    @Mapping(target = "downVotedAnswersId", ignore = true)
    @Mapping(target = "upVotedCommentsId", ignore = true)
    @Mapping(target = "downVotedCommentsId", ignore = true)
    Member toMember(RegisterDto registerDto);

    @Mapping(target = "viewedQuestions", ignore = true )
    @Mapping(target = "role", source = "account.role")
    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "name", source = "account.name")
    SessionMemberDto toSessionMember(Member member);

}
