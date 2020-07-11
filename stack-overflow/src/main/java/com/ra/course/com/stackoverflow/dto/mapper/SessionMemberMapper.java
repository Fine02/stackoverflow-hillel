package com.ra.course.com.stackoverflow.dto.mapper;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SessionMemberMapper {

    public SessionMemberDto getSessionMember(final MemberDto member){
        return new SessionMemberDto(member.getId(), member.getName(), new HashMap<>());
    }
}
