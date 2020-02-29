package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository<T>  {

    long save(T t);

    Optional<Member> findById(long id);

    void delete(Member member);

    Member update (Member member);

    List<Member> findAll();

    Optional<Member> findByMemberName(String name);

}
