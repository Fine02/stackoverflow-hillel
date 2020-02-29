package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Answer;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository<T>  {
    long save(T t);

    Optional<Answer> findById(long id);

    void delete(Answer answer);

    Answer update (Answer answer);

    List<Answer> findAll();

    List<Answer> listAnswersForQuestion(Question question);

    List<Answer> findAllMemberAnswers(Member Member);
}
