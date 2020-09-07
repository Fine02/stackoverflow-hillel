package com.ra.course.com.stackoverflow.service.member;

import com.ra.course.com.stackoverflow.dto.member.*;


public interface MemberService {

    SessionMemberDto registerMember(RegisterDto registerDto);

    SessionMemberDto loginMember(LogInDto logInDto);

    void deleteMember(SessionMemberDto sessionMemberDto, String password);

    void updateMember (UpdateDto updateDto, SessionMemberDto sessionMemberDto, String password);

    MemberDto getMemberById(Long id);

}
