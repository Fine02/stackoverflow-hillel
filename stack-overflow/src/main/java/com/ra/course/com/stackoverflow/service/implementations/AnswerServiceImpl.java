package com.ra.course.com.stackoverflow.service.implementations;

import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.service.interfaces.AnswerService;
import lombok.NonNull;

public class AnswerServiceImpl implements AnswerService<Answer, Question> {

    private final transient AnswerRepository answerRepo;
    private final transient QuestionRepository questionRepo;

    public AnswerServiceImpl(final AnswerRepository answerRepo, final QuestionRepository questionRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
    }


    /**Members able to add an answer to an open question.
     * If QuestionStatus is not OPEN, throws new QuestionClosedException()**/
    @Override
    public Answer addAnswerToQuestion(@NonNull final Answer answer, @NonNull final Question question) {

        if (!question.getStatus().equals(QuestionStatus.OPEN)) {
            throw new QuestionClosedException("addAnswerToQuestion: QuestionStatus is: " + question.getStatus());
        }

        question.getAnswerList().add(answer);
        questionRepo.update(question); //if it necessary

        return answerRepo.save(answer);
    }
}
