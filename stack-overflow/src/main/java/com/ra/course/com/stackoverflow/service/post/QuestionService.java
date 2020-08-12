package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;

public interface QuestionService {

    QuestionFullDto getQuestionById(Long id);

    QuestionFullDto createQuestion(CreateQuestionDto createQuestionDto, SessionMemberDto sessionMemberDto);

    QuestionFullDto updateQuestion(UpdateQuestionDto updateQuestionDto, Long questionId, SessionMemberDto sessionMember);

    QuestionFullDto deleteQuestion(Long questionId, SessionMemberDto sessionMember);

    QuestionFullDto closeQuestion(Long questionId, SessionMemberDto sessionMember);

}
