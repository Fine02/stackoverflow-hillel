package com.ra.course.com.stackoverflow.service.storage;

import com.ra.course.com.stackoverflow.entity.Member;

import java.util.Optional;

public interface MemberStorageService {

    Optional<Member> findMemberByEmail(String email);

    Member saveMemberToDB(Member member);
}
