package com.ra.course.com.stackoverflow.repository.implementation;


import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository<Member> {

    @Override
    public long save(Member question) {
        return 0L;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Member question) {

    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> findByMemberName(String name) {
        return Optional.empty();
    }

    @Override
    public long getNextId() {
        return 2L;
    }
}
