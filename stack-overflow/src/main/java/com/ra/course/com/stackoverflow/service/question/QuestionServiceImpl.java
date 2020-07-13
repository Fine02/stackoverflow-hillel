package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
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

import com.ra.course.com.stackoverflow.repository.TagQuestionRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final transient AnswerRepository answerRepo;
    private final transient QuestionRepository questionRepo;
    private final transient TagRepository tagRepo;
    private final transient TagQuestionRepository tagQuestionRepo;


    /**Members able to add an answer to an open question.
     * If QuestionStatus is not OPEN, throws new QuestionClosedException()**/
    @Override
    public Question addAnswerToQuestion(@NonNull final Answer answer) {

        final var questionFromBD = questionRepo.findById(answer.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add answer to nonexistent question."));

        if (!questionFromBD.getStatus().equals(QuestionStatus.OPEN)) {
            throw new QuestionClosedException("Forbidden. Answer can be added only to open question. Status of question is " + questionFromBD.getStatus());
        }

        questionFromBD.getAnswerList().add(answer);

        answerRepo.save(answer);

        return questionFromBD;
    }


    /**Members can add tags to their questions. A tag is a word or phrase that describes the topic of the question.**/
    @Override
    public void addTagToQuestion(final String tagName, final QuestionDto question) {

        final var questionFromDB = questionRepo.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in DB. Can't add tag to nonexistent question"));

        final var tagMatch = questionFromDB.getTagList().stream()
                .anyMatch(tag -> tag.getName().equalsIgnoreCase(tagName));

        if (tagMatch){
            throw new TagAlreadyAddedException("Tag " + tagName + " already added to this question.");
        }

        final var tagFromDB = tagRepo.findByTagName(tagName)
                .orElseGet(() -> tagRepo.save(new Tag(null, tagName, null)));

        tagQuestionRepo.save(tagFromDB, questionFromDB);
    }
}
