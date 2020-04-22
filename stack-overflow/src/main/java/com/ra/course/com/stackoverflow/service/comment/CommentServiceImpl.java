package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;

import com.ra.course.com.stackoverflow.exception.service.AnswerNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;

import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final transient CommentRepository commentRepo;
    private final transient QuestionRepository questionRepo;
    private final transient AnswerRepository answerRepo;


    /**Members can add comments to any question.**/
    @Override
    public Question addCommentToQuestion(@NonNull final Comment comment, @NonNull final Question question) {

        final var questionFromDB = questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add comment to nonexistent question"));

        questionFromDB.getCommentList().add(comment);

        commentRepo.save(comment);

        return questionFromDB;
    }


    /**Members can add comments to any answer.**/
    @Override
    public Answer addCommentToAnswer(@NonNull final Comment comment, @NonNull final Answer answer) {

        final var answerFromDB = answerRepo.findById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found in DB. Can't add comment to nonexistent answer"));

        answerFromDB.getComments().add(comment);

        commentRepo.save(comment);

        return answerFromDB;
    }
}
