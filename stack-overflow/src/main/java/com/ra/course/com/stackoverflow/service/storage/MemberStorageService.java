package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;

import java.util.List;

public interface MemberStorageService {

    MemberDto registerMember(RegisterDto registerDto);

    MemberDto findMemberById(long id);

    MemberDto loginMember(LogInDto logInDto);

    void deleteMember(long id, String password);

    MemberDto updateMember (UpdateDto updateDto, String password);

    List<MemberDto> findByMemberName(String name);
}
