package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository<T>  {

    long save(T t);

    Optional<T> findById(long id);

    void delete(T t);

    List<T> findAll();

    Optional<Member> findByMemberName(String name);

}
