package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberStorageService {

    MemberDto saveMemberToDB(MemberDto memberDto);

    MemberDto findMemberById(long id);

    MemberDto loginMember(String email, String password);

    void deleteMember(Member member);

    void updateMember (Member member);

    List<MemberDto> findByMemberName(String name);
}
