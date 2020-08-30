package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;

public interface AdminService {

    void blockMember(Long memberId, SessionMemberDto memberDto);

    void unblockMember(Long memberId, SessionMemberDto memberDto);

    void setMemberAsModer(Long memberId, SessionMemberDto memberDto);

    void setMemberAsAdmin(Long memberId, SessionMemberDto memberDto);

    void setMemberAsUser(Long memberId, SessionMemberDto memberDto);
}
