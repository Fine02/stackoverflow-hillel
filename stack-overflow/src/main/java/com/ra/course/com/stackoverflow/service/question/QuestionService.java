package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;

public interface QuestionService {

    Answer addAnswerToQuestion(Answer answer);

    boolean addTagToQuestion(Tag tag, Question question);
}
