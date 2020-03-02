package com.ra.course.com.stackoverflow.service.interfaces;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;

public interface CommentService<T> {

    T addCommentToQuestion(T t, Question question);

    T addCommentToAnswer(T t, Answer answer);
}
