package com.ra.course.com.stackoverflow.repository.implementation;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class QuestionRepositoryImpl implements QuestionRepository {

    @Override
    public Question save(final Question question) throws DataBaseOperationException {
        return null;
    }

    @Override
    public Optional<Question> findById(final long id) {
        return Optional.empty();
    }


    @Override
    public void delete(final Question question) {
        //delete question
    }

    @Override
    public Question update(final Question question) {
        return null;
    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public List<Question> findAllByTitle(final String text) {
        return null;
    }

    @Override
    public List<Question> findAllMemberQuestions(final Member member) {
        return null;
    }


    @Override
    public long getNextId() {
        return 1L;
    }
}
