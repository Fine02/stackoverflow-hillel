package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

public interface AnswerService {

    void addAnswer(CreateDto createDto, Long questionId, SessionMemberDto sessionMemberDto);

    void acceptAnswer(Long answerId, SessionMemberDto sessionMemberDto);

    void deleteAnswer(Long answerId, SessionMemberDto sessionMemberDto);
}
