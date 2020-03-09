package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.exception.service.TagNotFoundException;

public interface QuestionService {

    Answer addAnswerToQuestion(Answer answer) throws QuestionClosedException, QuestionNotFoundException;

    boolean addTagToQuestion(Tag tag, Question question) throws TagAlreadyAddedException, QuestionNotFoundException, TagNotFoundException;
}
