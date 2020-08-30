package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.bounty.CreateBountyDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.Answer;

public interface BountyService {

    QuestionFullDto addBounty(Long questionId, CreateBountyDto bountyDto, SessionMemberDto sessionMemberDto);

    void chargeBounty(Answer answer);

    void deleteBounty(Long questionId, SessionMemberDto sessionMemberDto);
}