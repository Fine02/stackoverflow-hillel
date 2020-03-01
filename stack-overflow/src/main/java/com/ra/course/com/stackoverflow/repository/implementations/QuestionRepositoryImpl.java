package com.ra.course.com.stackoverflow.repository.implementations;

import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.exception.repository.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class QuestionRepositoryImpl implements QuestionRepository {

    private final transient List<Question> questions;

    public QuestionRepositoryImpl(final List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public Question save(final Question question) {

        questions.add(question);

        return questions.get((int) question.getId());
    }

    @Override
    public long getNextId() {
        return questions.size() + 1;
    }

    @Override
    public Optional<Question> findById(final long id) {
        return Optional.empty();
    }

    @Override
    public void delete(final Question question) {
        questions.remove((int) question.getId());
    }

    @Override
    public Question update(final Question question) {
        final int id = (int) question.getId();

        if (questions.get(id) == null) {
            throw new QuestionNotFoundException("Can't find question in QuestionRepository");
        }

        questions.add(id, question);

        return questions.get(id);
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public List<Question> findInTitle(final String text) {
        return null;
    }

    @Override
    public List<Question> findAllMemberQuestions(final Member member) {
        return member.getQuestions();
    }
}
