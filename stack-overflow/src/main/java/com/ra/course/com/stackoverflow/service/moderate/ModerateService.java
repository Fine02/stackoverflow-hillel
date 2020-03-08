package com.ra.course.com.stackoverflow.service.moderate;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

public interface ModerateService {
    Question closeQuestion(Question question) throws QuestionNotFoundException;

    Question undeleteQuestion(Question question) throws QuestionNotFoundException;

    Question reopenQuestion(Question question) throws QuestionNotFoundException;
}
