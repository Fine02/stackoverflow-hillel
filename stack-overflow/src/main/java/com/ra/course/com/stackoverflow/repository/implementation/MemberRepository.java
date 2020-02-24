package com.ra.course.com.stackoverflow.repository.implementation;

import com.ra.course.com.stackoverflow.entity.Searchable;
import com.ra.course.com.stackoverflow.entity.implementation.Member;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;

public class MemberRepository implements GeneralRepository<Member>{

    @Override
    public Member save(final Member member) throws RepositoryException {
        return null;
    }

    @Override
    public long getNextId() throws RepositoryException {
        return 1L;
    }

    public void saveQuestion() throws RepositoryException {
        return;
    }
}
