package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  {

    Member save(Member member);

    Optional<Member> findById(long id);

    void delete(Member t);

    List<Member> findAll();

    Optional<Member> findByMemberName(String name);

    long getNextId();
}
