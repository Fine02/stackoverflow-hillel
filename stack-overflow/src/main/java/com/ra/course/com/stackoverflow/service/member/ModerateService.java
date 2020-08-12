package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;

public interface ModerateService {
    QuestionFullDto closeQuestion(Long questionId, SessionMemberDto memberDto);

    QuestionFullDto undeleteQuestion(Long questionId, SessionMemberDto memberDto);

    QuestionFullDto reopenQuestion(Long questionId, SessionMemberDto memberDto);
}
