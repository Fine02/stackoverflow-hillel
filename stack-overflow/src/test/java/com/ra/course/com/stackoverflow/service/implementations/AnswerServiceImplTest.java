package com.ra.course.com.stackoverflow.service.implementations;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnswerServiceImplTest {
    private AnswerServiceImpl answerService;
    private long id = 1L;
    private String description = "some description";
    private AnswerRepository answerRepository;

    private Account account = Account.builder()
            .password("password")
            .email("email")
            .name("name")
            .build();

    private Member member = Member.builder()
            .id(id)
            .account(account)
            .build();

    private Question question = Question.builder()
            .id(id)
            .description(description)
            .title("title")
            .author(member)
            .build();

    private Answer answer = Answer.builder()
            .id(id)
            .answerText("answer_text")
            .creationDate(LocalDateTime.now())
            .author(member)
            .question(question)
            .photos(new ArrayList<>())
            .comments(new ArrayList<>())
            .notifications(new ArrayList<>())
            .build();


    @BeforeEach
    public void setUp() {
        answerRepository = mock(AnswerRepository.class);
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        answerService = new AnswerServiceImpl(answerRepository, questionRepository);
    }

    @Test
    public void whenAddAnswerToOpenQuestionThatNotExistThenReturnNewAnswerWithId() {
        question.setStatus(QuestionStatus.OPEN);

        //when
        when(answerRepository.save(answer)).thenReturn(answer);

        //then
        var resultAnswer = answerService.addAnswerToQuestion(answer, question);
        assertThat(resultAnswer.getId()).isEqualTo(1L);
    }


    @Test
    public void whenAddAnswerToNotOpenQuestionThenExceptionThrown() {
        question.setStatus(QuestionStatus.CLOSE);

        assertThatThrownBy(() -> answerService.addAnswerToQuestion(answer, question))
                .isInstanceOf(QuestionClosedException.class)
                .hasMessage("addAnswerToQuestion: QuestionStatus is: CLOSE");
    }

    @Test
    public void shouldThrowExceptionWhenAnswerIsNull() {
        question.setStatus(QuestionStatus.OPEN);

        assertThatThrownBy(() -> answerService.addAnswerToQuestion(null, question))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("answer is marked @NonNull but is null");
    }

    @Test
    public void shouldThrowExceptionWhenQuestionIsNull() {
        question.setStatus(QuestionStatus.OPEN);

        assertThatThrownBy(() -> answerService.addAnswerToQuestion(answer, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("question is marked @NonNull but is null");
    }
}
