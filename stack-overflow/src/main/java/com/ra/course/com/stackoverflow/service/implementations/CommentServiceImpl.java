package com.ra.course.com.stackoverflow.service.implementations;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;

import com.ra.course.com.stackoverflow.service.interfaces.CommentService;

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
    public Comment addCommentToQuestion(@NonNull final Comment comment, @NonNull final Question question) throws QuestionNotFoundException {

        final var questionFromDB = questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add comment to nonexistent question"));


        questionFromDB.getCommentList().add(comment);
        questionRepo.update(questionFromDB);
        commentRepo.save(comment);

        return comment;
    }


    /**Members can add comments to any answer.**/
    @Override
    public Comment addCommentToAnswer(@NonNull final Comment comment, @NonNull final Answer answer) throws AnswerNotFoundException {

        final var answerFromDB = answerRepo.findById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found in DB. Can't add comment to nonexistent answer"));

        answer.getComments().add(comment);
        answerRepo.update(answerFromDB);
        commentRepo.save(comment);

        return comment;
    }
}
