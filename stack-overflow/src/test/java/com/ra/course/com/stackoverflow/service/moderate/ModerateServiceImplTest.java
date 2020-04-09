package com.ra.course.com.stackoverflow.service.moderate;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.QuestionNotFoundException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ModerateServiceImplTest {
    private QuestionRepository questionRepository;
    private final String TAG_NAME = "testTag";
    private long id = 1L;
    private String description = "description";
    private ModerateService moderateService;

    private Account account = Account.builder()
                                     .password("password")
                                     .email("email")
                                     .name("name")
                                     .build();

    private Member member = Member.builder()
                                  .id(id)
                                  .account(account)
                                  .build();

    private Tag tag = new Tag(id, TAG_NAME, description);

    private Question question = constructQuestion().build();

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        moderateService = new ModerateServiceImpl(questionRepository);
    }

    @Test
    public void shouldCloseQuestion() {
        Question expectedResponse = constructCloseQuestion(question);
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(questionRepository.update(question)).thenReturn(expectedResponse);

        Question actualResponse = moderateService.closeQuestion(question);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(questionRepository).findById(question.getId());
        verify(questionRepository).update(question);
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldThrowExceptionIfCloseQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moderateService.closeQuestion(question))
                .isInstanceOf(QuestionNotFoundException.class);

        verify(questionRepository).findById(question.getId());
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldReopenQuestion() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(questionRepository.update(question)).thenReturn(question);

        Question actualResponse = moderateService.reopenQuestion(question);

        assertThat(actualResponse).isEqualTo(question);

        verify(questionRepository).findById(question.getId());
        verify(questionRepository).update(question);
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldThrowExceptionIfReopenQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moderateService.reopenQuestion(question))
                .isInstanceOf(QuestionNotFoundException.class);

        verify(questionRepository).findById(question.getId());
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldUnDeleteQuestion() {
        Question expectedResponse = constructUnDeleteQuestion(question);
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(questionRepository.update(question)).thenReturn(question);

        Question actualResponse = moderateService.undeleteQuestion(question);

        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(questionRepository).findById(question.getId());
        verify(questionRepository).update(question);
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldThrowExceptionIfUnDeleteQuestionNotFound() {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moderateService.undeleteQuestion(question))
                .isInstanceOf(QuestionNotFoundException.class);

        verify(questionRepository).findById(question.getId());
        verifyNoMoreInteractions(questionRepository);
    }

    private Question constructUnDeleteQuestion(Question question) {
        Question.QuestionBuilder<?, ?> question1 = constructQuestion();
        return question1.status(QuestionStatus.ON_HOLD).build();
    }

    public Question constructCloseQuestion(Question question) {
        Question.QuestionBuilder<?, ?> question1 = constructQuestion();
        return question1.status(QuestionStatus.CLOSE).build();
    }

    private Question.QuestionBuilder<?, ?> constructQuestion() {
        return Question.builder()
                       .id(id)
                       .description(description)
                       .title("title")
                       .author(member)
                       .tagList(List.of(tag));

    }

}