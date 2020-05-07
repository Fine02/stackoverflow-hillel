package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberStorageService {

    Member saveMemberToDB(Member member);

    Optional<Member> findMemberById(long id);

    Optional<Member> findMemberByEmail(String email);

    void deleteMember(Member member);

    void updateMember (Member member);

    List<Member> findByMemberName(String name);
}
