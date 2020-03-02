package com.ra.course.com.stackoverflow.service.implementations;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.interfaces.CommentService;
import lombok.NonNull;

public class CommentServiceImpl implements CommentService<Comment> {

    private final transient CommentRepository commentRepo;
    private final transient QuestionRepository questionRepo;
    private final transient AnswerRepository answerRepo;

    public CommentServiceImpl(final CommentRepository commentRepo, final QuestionRepository questionRepo, final AnswerRepository answerRepo) {
        this.commentRepo = commentRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }


    /**Members can add comments to any question.**/
    @Override
    public Comment addCommentToQuestion(@NonNull final Comment comment, @NonNull final Question question) {

        question.getCommentList().add(comment);
        questionRepo.update(question); //if it necessary

        return commentRepo.save(comment);
    }


    /**Members can add comments to any answer.**/
    @Override
    public Comment addCommentToAnswer(@NonNull final Comment comment, @NonNull final Answer answer) {

        answer.getComments().add(comment);
        answerRepo.update(answer); //if it necessary

        return commentRepo.save(comment);
    }
}
