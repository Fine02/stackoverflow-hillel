package com.ra.course.com.stackoverflow.repository.interfaces;

import com.ra.course.com.stackoverflow.dto.QuestionSaveDto;
import com.ra.course.com.stackoverflow.entity.implementations.Member;
import com.ra.course.com.stackoverflow.entity.implementations.Question;
import com.ra.course.com.stackoverflow.entity.implementations.Tag;

import java.util.List;

public interface QuestionRepository extends GeneralRepository<QuestionSaveDto, Question> {
    List<Question> findInTitle(String text);

    List<Tag> findTagsByQuestion(Question question);

    List<Question> findMemberQuestions(Member member);
}
