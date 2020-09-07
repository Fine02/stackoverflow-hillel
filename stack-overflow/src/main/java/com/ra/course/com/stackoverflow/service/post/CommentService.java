package com.ra.course.com.stackoverflow.service.post;

import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

public interface CommentService {

    void addCommentToQuestion(CreateDto commentDto, Long questionId, SessionMemberDto sessionMemberDto);

    void addCommentToAnswer(CreateDto commentDto, Long answerId, SessionMemberDto sessionMemberDto);

    void deleteComment(Long commentId, SessionMemberDto sessionMemberDto);
}
