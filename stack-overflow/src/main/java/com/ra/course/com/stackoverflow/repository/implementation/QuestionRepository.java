package com.ra.course.com.stackoverflow.repository.implementation;

import com.ra.course.com.stackoverflow.entity.Search;
import com.ra.course.com.stackoverflow.exception.repository.RepositoryException;
import com.ra.course.com.stackoverflow.repository.GeneralRepository;

public class QuestionRepository implements GeneralRepository<Search>{

    @Override
    public Search save(final Search question) throws RepositoryException {
        return null;
    }

    @Override
    public long getNextId(final Search question) throws RepositoryException {
        return 1L;
    }
}
