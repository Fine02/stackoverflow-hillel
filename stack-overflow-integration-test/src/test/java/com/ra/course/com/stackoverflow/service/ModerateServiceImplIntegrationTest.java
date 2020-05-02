package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.service.moderate.ModerateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class ModerateServiceImplIntegrationTest {

    @Autowired
    private ModerateService moderateService;
    private Question givenQuestion;
    private Question expectedQuestion;
    private Question notInDbQuestion;

    private final static long QUESTION_ID = 1L;
    private final static String QUESTION_TITLE = "First question title";
    private final static String QUESTION_DESCRIPT = "First question description";
    private final static long QUESTION_AUTH_ID = 1L;


    @BeforeEach
    void setUp() {
        givenQuestion = createQuestion(QUESTION_ID);
        expectedQuestion = createQuestion(QUESTION_ID);
        notInDbQuestion = createQuestion(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("[closeQuestion] when closeQuestion() invoked then return question with CLOSE status")
    public void closeQuestionTest() {
        //given
        expectedQuestion.setStatus(QuestionStatus.CLOSE);

        //when
        Question actualQuestion = moderateService.closeQuestion(givenQuestion);

        //then
        assertThat(actualQuestion.getStatus()).isEqualTo(expectedQuestion.getStatus());
    }

    @Test
    @DisplayName("[closeQuestion] when not existing question passed to closeQuestion() then throw expected exception")
    public void closeQuestionExceptionTest() {
        assertThatThrownBy(() -> moderateService.closeQuestion(notInDbQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    @DisplayName("[reopenQuestion] when reopenQuestion() invoked then return question with OPEN status")
    public void reopenQuestionTest() {
        //given
        expectedQuestion.setStatus(QuestionStatus.OPEN);

        //when
        Question actualQuestion = moderateService.reopenQuestion(givenQuestion);

        //then
        assertThat(actualQuestion.getStatus()).isEqualTo(expectedQuestion.getStatus());
    }

    @Test
    @DisplayName("[reopenQuestion] when not existing question passed to reopenQuestion() then throw expected exception")
    public void reopenQuestionExceptionTest() {
        assertThatThrownBy(() -> moderateService.reopenQuestion(notInDbQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    @DisplayName("[undeleteQuestion] when undeleteQuestion() invoked then return question with ON_HOLD status")
    public void undeleteQuestionTest() {
        //given
        expectedQuestion.setStatus(QuestionStatus.ON_HOLD);

        //when
        Question actualQuestion = moderateService.undeleteQuestion(givenQuestion);

        //then
        assertThat(actualQuestion.getStatus()).isEqualTo(expectedQuestion.getStatus());
    }

    @Test
    @DisplayName("[undeleteQuestion] when not existing question passed to undeleteQuestion() then throw expected exception")
    public void undeleteQuestionExceptionTest() {
        assertThatThrownBy(() -> moderateService.undeleteQuestion(notInDbQuestion))
                .isInstanceOf(QuestionNotFoundException.class);
    }


    private static Question createQuestion(final long id) {
        return Question.builder()
                .id(id)
                .description(QUESTION_DESCRIPT)
                .title(QUESTION_TITLE)
                .authorId(QUESTION_AUTH_ID)
                .build();
    }
}
