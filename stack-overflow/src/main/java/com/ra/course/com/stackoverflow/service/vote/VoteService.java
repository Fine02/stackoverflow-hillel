package com.ra.course.com.stackoverflow.service.vote;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

public interface VoteService<T> {

    void upVote(Long postId, SessionMemberDto member);

    void downVote (Long postId, SessionMemberDto member);

}
