package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository <T, M>  {

    long save(final T t);

    Optional<Question> findById(final long id);

    void delete(final T question);

    List<Question> findAll();

    List<Question> findInTitle(final String text);

    List<Question> findMemberQuestions(final M member);

    long getNextId();
}
