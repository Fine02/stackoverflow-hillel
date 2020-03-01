package com.ra.course.com.stackoverflow.repository.implementations;

import com.ra.course.com.stackoverflow.entity.implementations.Answer;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.exception.repository.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;

import java.util.List;
import java.util.Optional;

public class AnswerRepositoryImpl implements AnswerRepository {

    private final transient List<Answer> answers;

    public AnswerRepositoryImpl(final List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public Answer save(final Answer answer) {

        answers.add(answer);

        return answers.get((int) answer.getId());
    }


    @Override
    public long getNextId() {
        return answers.size() + 1;
    }

    @Override
    public Optional<Answer> findById(final long id) {
        return Optional.empty();
    }

    @Override
    public void delete(final Answer answer) {
        answers.remove((int) answer.getId());

    }

    @Override
    public Answer update(final Answer answer) {
        final int id = (int) answer.getId();

        if (answers.get(id) == null) {
            throw new AnswerNotFoundException("Can't find answer in AnswerRepository");
        }

        answers.add(id, answer);

        return answers.get(id);
    }

    @Override
    public List<Answer> findAll() {
        return answers;
    }


    @Override
    public List<Answer> listAnswersForQuestion(final Question question) {
        return question.getAnswerList();
    }

    @Override
    public List<Answer> findAllMemberAnswers(final Member member) {
        return member.getAnswers();
    }
}
