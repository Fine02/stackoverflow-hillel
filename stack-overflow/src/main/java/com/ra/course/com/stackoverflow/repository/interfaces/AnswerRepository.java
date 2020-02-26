package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.dto.AnswerSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Answer;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;

import java.util.List;

public interface AnswerRepository extends GeneralRepository<AnswerSaveDto, Answer> {

    List<Answer> listAnswersForQuestion(Question question);

    List<Answer> findAllMemberAnswers(Member Member);
}
