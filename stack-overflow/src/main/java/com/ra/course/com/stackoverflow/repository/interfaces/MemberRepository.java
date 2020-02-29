package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository<T>  {

    long save(final T t);

    Optional<T> findById(final long id);

    void delete(final T t);

    List<T> findAll();

    Optional<Member> findByMemberName(final String name);

    long getNextId();
}
