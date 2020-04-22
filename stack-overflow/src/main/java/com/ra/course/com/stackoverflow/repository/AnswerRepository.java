package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository  {

    Answer save(Answer answer);

    Optional<Answer> findById(Long id);

    void delete(Answer answer);

    void update (Answer answer);

    List<Answer> findByQuestionId(Long id);

    List<Answer> findByMemberId(Long id);

}
