package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository <T>  {

    long save(T t);

    Optional<Question> findById(long id);

    void delete(Question question);

    List<Question> findAll();

    List<Question> findInTitle(String text);

    List<Question> findMemberQuestions(Member member);
}
