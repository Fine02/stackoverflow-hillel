package com.ra.course.com.stackoverflow.service.moderate;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.CLOSE;
import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.ON_HOLD;
import static com.ra.course.com.stackoverflow.entity.enums.QuestionStatus.OPEN;

@AllArgsConstructor
@Service
public class ModerateServiceImpl implements ModerateService {

    private final QuestionRepository questionRepo;

    @Override
    public Question closeQuestion(final Question question) {
        final Question questionFromDB = findQuestionById(question);

        questionFromDB.setStatus(CLOSE);

        return questionRepo.update(questionFromDB);
    }

    @Override
    public Question undeleteQuestion(final Question question) {
       final Question questionFromDB = findQuestionById(question);

        questionFromDB.setStatus(ON_HOLD);

        return questionRepo.update(questionFromDB);
    }

    @Override
    public Question reopenQuestion(final Question question) {
       final Question questionFromDB = findQuestionById(question);

        questionFromDB.setStatus(OPEN);

        return questionRepo.update(questionFromDB);
    }

    private Question findQuestionById(final Question question) {
        return questionRepo.findById(question.getId())
                           .orElseThrow(() -> new QuestionNotFoundException("No such question with in DB with Id:" + question.getId()));
    }
}
