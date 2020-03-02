package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository  {

    Answer save(Answer answer);

    long getNextId();

    Optional<Answer> findById(long id);

    void delete(Answer answer);

    Answer update (Answer answer);

    List<Answer> findAll();

    List<Answer> listAnswersForQuestion(Question question);

    List<Answer> findAllMemberAnswers(Member member);

    List<Answer> findByQuestionId(long id);
}
