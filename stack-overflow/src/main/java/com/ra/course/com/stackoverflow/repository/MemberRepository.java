package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    void delete(Member member);

    void update (Member member);

    List<Member> findByMemberName(String name);

}
