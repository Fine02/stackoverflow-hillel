package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;

public interface QuestionService {

    Question addAnswerToQuestion(Answer answer);

    void addTagToQuestion(String tagName, QuestionDto question);
}
