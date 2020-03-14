package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.repository.QuestionRepositoryException;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final transient AnswerRepository answerRepo;
    private final transient QuestionRepository questionRepo;
    private final transient TagRepository tagRepo;


    /**Members able to add an answer to an open question.
     * If QuestionStatus is not OPEN, throws new QuestionClosedException()**/
    @Override
    public Answer addAnswerToQuestion(@NonNull final Answer answer) {

        final var questionFromBD = questionRepo.findById(answer.getQuestion().getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add answer to nonexistent question."));

        if (!questionFromBD.getStatus().equals(QuestionStatus.OPEN)) {
            throw new QuestionClosedException("Forbidden. Answer can be added only to open question. Status of question is " + questionFromBD.getStatus());
        }

        questionFromBD.getAnswerList().add(answer);
        answer.getAuthor().getAnswers().add(answer);
        answerRepo.update(answer);
        questionRepo.update(questionFromBD);

        return answerRepo.save(answer);
    }


    /**Members can add tags to their questions. A tag is a word or phrase that describes the topic of the question.**/
    @Override
    public boolean addTagToQuestion(@NonNull final Tag tag, @NonNull final Question question) {

        final var questionFromDB = questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add tag to nonexistent question"));

        if (questionFromDB.getTagList().contains(tag)){
            throw new TagAlreadyAddedException("Tag " + tag.getName() + " already added to this question.");
        }

        final var tagFromDB = tagRepo.findById(tag.getId())
                .orElseGet(() -> tagRepo.save(tag));

        questionFromDB.getTagList().add(tagFromDB);

        try {
            questionRepo.update(questionFromDB);
        }catch (QuestionRepositoryException e) {
            tagRepo.delete(tag);
            return false;
        }

        return true;
    }
}
