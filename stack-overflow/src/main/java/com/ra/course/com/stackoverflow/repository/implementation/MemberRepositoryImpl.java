package com.ra.course.com.stackoverflow.repository.implementation;


import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public Member save(Member question) {
        return null;
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
        return 1L;
    }
}
