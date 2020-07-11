package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;

public interface MemberService {

    QuestionDto createQuestion(CreateQuestionDto createQuestionDto, SessionMemberDto member);

}
