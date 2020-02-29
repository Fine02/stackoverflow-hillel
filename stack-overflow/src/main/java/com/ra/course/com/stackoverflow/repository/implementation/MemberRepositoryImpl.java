package com.ra.course.com.stackoverflow.repository.implementation;


import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public Member save(final Member question) {
        return null;
    }

    @Override
    public Optional<Member> findById(final long id) {
        return Optional.empty();
    }

    @Override
    public void delete(final Member member) {
        //delete member
    }

    @Override
    public Member update(final Member member) throws DataBaseOperationException {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> findByMemberName(final String name) {
        return Optional.empty();
    }

    @Override
    public long getNextId() {
        return 1L;
    }
}
