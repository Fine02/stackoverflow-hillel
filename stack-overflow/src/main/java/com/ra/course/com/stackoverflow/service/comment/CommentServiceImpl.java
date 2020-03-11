package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;


import lombok.NonNull;

public class CommentServiceImpl implements CommentService {

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

        final var questionFromDB = questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add comment to nonexistent question"));


        questionFromDB.getCommentList().add(comment);
        questionRepo.update(questionFromDB);


        return commentRepo.save(comment);
    }


    /**Members can add comments to any answer.**/
    @Override
    public Comment addCommentToAnswer(@NonNull final Comment comment, @NonNull final Answer answer) {

        final var answerFromDB = answerRepo.findById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found in DB. Can't add comment to nonexistent answer"));

        answerFromDB.getComments().add(comment);
        answerRepo.update(answerFromDB);


        return commentRepo.save(comment);
    }
}
