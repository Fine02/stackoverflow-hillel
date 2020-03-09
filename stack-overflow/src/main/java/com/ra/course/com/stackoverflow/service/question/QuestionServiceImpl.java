package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.TagRepository;
import lombok.NonNull;

public class QuestionServiceImpl implements QuestionService {

    private final transient AnswerRepository answerRepo;
    private final transient QuestionRepository questionRepo;
    private final transient TagRepository tagRepo;


    public QuestionServiceImpl(final AnswerRepository answerRepo,
                               final QuestionRepository questionRepo, final TagRepository tagRepo) {
        this.answerRepo = answerRepo;
        this.questionRepo = questionRepo;
        this.tagRepo = tagRepo;
    }


    /**Members able to add an answer to an open question.
     * If QuestionStatus is not OPEN, throws new QuestionClosedException()**/
    @Override
    public Answer addAnswerToQuestion(@NonNull final Answer answer) throws QuestionClosedException, QuestionNotFoundException {

        final var question = questionRepo.findById(answer.getQuestion().getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add answer to nonexistent question."));


        if (!question.getStatus().equals(QuestionStatus.OPEN)) {
            throw new QuestionClosedException("Forbidden. Answer can be added only to open question. Status of question is " + question.getStatus());
        }


        question.getAnswerList().add(answer);
        question.getAuthor().getAnswers().add(answer);
        questionRepo.update(question); //if it necessary



        return answerRepo.save(answer);
    }


    /**Members can add tags to their questions. A tag is a word or phrase that describes the topic of the question.**/
    @Override
    public boolean addTagToQuestion(@NonNull final Tag tag, @NonNull final Question question) throws TagAlreadyAddedException,
            QuestionNotFoundException {

        questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add tag to nonexistent question"));


        if (question.getTagList().contains(tag)){
            throw new TagAlreadyAddedException("Tag " + tag.getName() + " already added to this question.");
        }else {
            final var tagFromDB = tagRepo.findById(tag.getId())
                    .orElseGet(() -> tagRepo.save(tag));

            question.getTagList().add(tagFromDB);
            questionRepo.update(question);

            return true;
        }

    }

}
