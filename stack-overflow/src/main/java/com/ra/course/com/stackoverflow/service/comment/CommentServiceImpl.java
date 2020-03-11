package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;


import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final transient CommentRepository commentRepo;
    private final transient QuestionRepository questionRepo;
    private final transient AnswerRepository answerRepo;


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
