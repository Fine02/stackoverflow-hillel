package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

public interface CommentService {

    Comment addCommentToQuestion(Comment comment, Question question) throws QuestionNotFoundException;

    Comment addCommentToAnswer(Comment comment, Answer answer) throws QuestionNotFoundException, AnswerNotFoundException;
}
