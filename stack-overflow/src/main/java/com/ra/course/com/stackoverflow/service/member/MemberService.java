package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.QuestionDto;

public interface MemberService {

    QuestionDto createQuestion(CreateQuestionDto createQuestionDto, MemberDto memberDto);

}
