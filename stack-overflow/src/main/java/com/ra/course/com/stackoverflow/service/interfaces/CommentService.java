package com.ra.course.com.stackoverflow.service.interfaces;

import com.ra.course.com.stackoverflow.entity.implementations.Answer;
import com.ra.course.com.stackoverflow.entity.implementations.Question;

public interface CommentService<T> {

    T addCommentToQuestion(T t, Question question);

    T addCommentToAnswer(T t, Answer answer);
}
