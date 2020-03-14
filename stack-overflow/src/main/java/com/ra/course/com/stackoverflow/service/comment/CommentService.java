package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

public interface CommentService {

    Comment addCommentToQuestion(Comment comment, Question question);

    Comment addCommentToAnswer(Comment comment, Answer answer);
}
