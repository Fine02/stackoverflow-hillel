package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.entity.Member;

import java.util.List;

public interface MemberStorageService {

    MemberDto saveMemberToDB(RegisterDto registerDto);

    MemberDto findMemberById(long id);

    MemberDto loginMember(LogInDto logInDto);

    void deleteMember(Member member);

    void updateMember (Member member);

    List<MemberDto> findByMemberName(String name);
}
