package com.ra.course.com.stackoverflow.repository.implementation;

import com.ra.course.com.stackoverflow.entity.Searchable;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;

public class QuestionRepository implements GeneralRepository<Searchable>{

    @Override
    public Searchable save(final Searchable question) throws RepositoryException {
        return null;
    }

    @Override
    public long getNextId() throws RepositoryException {
        return 1L;
    }
}
