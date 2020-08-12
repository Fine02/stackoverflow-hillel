package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;

public interface VoteToCloseService {

    void voteToClose(Long questionId, SessionMemberDto sessionMemberDto, QuestionClosingRemark remark);
}
