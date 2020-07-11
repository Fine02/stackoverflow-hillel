package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.mapper.impl.QuestionMapper;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private transient final QuestionRepository questionRep;
    private transient final MemberRepository memberRep;
    private transient final QuestionMapper questionMapper;

    @Override
    public QuestionDto createQuestion(final CreateQuestionDto createQuestionDto,
                                      final SessionMemberDto member) {

        checkMember(member);

        final Question newQuestion = questionMapper.questionFromCreateDto(createQuestionDto);
        newQuestion.setAuthorId(member.getId());

        final Question postedQuestion = questionRep.save(newQuestion);

        return questionMapper.dtoFromEntity(postedQuestion);
    }

    private void checkMember(final SessionMemberDto member) {
        final var optionalMember = memberRep.findById(member.getId());
        optionalMember.orElseThrow(
                () -> new MemberNotFoundException("No such member in DB"));

    }
}
