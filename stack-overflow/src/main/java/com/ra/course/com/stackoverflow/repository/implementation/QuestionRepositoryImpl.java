package com.ra.course.com.stackoverflow.repository.implementation;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class QuestionRepositoryImpl implements QuestionRepository {

    @Override
    public Question save(final Question question) {
        return null;
    }

    @Override
    public Optional<Question> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(final Question question) {

    }

    @Override
    public List<Question> findAll() {
        return null;
    }

    @Override
    public List<Question> findInTitle(final String text) {
        return null;
    }

    @Override
    public List<Question> findMemberQuestions(Member member) {
        return null;
    }


    @Override
    public long getNextId() {
        return 1L;
    }
}
