package com.ra.course.com.stackoverflow.service.moderate;

import com.ra.course.com.stackoverflow.entity.Question;

public interface ModerateService {
    Question closeQuestion(Question question);

    Question undeleteQuestion(Question question);

    Question reopenQuestion(Question question);
}
