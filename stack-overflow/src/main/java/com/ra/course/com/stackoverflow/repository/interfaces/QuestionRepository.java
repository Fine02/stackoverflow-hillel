package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    Question save(Question question);

    Optional<Question> findById(long id);

    void delete(Question question);

    List<Question> findAll();

    List<Question> findInTitle(String text);

    List<Question> findMemberQuestions(Member member);

    long getNextId();
}
