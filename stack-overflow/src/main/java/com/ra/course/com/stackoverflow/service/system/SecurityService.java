package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.*;

public interface SecurityService {

    void checkModer(SessionMemberDto sessionMemberDto);

    Member checkAdminAndReturnMember(SessionMemberDto sessionMemberDto);

    Member checkStatusAndReturnMember(SessionMemberDto sessionMemberDto);

    Question checkRightOfMemberAndReturnQuestion(SessionMemberDto sessionMemberDto, Long questionId);

    Answer checkRightOfMemberAndReturnAnswer(SessionMemberDto sessionMemberDto, Long answerId);

    Comment checkRightOfMemberAndReturnComment(SessionMemberDto sessionMemberDto, Long commentId);

    Bounty checkRightOfMemberAndReturnBounty(SessionMemberDto sessionMemberDto, Long bountyId);

}
