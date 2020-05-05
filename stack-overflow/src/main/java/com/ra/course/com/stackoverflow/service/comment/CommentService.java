package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

public interface CommentService {

    Question addCommentToQuestion(Comment comment, Question question);

    Answer addCommentToAnswer(Comment comment, Answer answer);
}
