package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.*;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;

public class QuestionServiceImplTest {
    private QuestionServiceImpl questionService;
    private final long ID = 1L;
    private String description = "some description";
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private TagRepository tagRepository;

    private Account account = Account.builder()
            .password("password")
            .email("email")
            .name("name")
            .build();

    private Member member = Member.builder()
            .id(ID)
            .account(account)
            .build();

    private Question question = Question.builder()
            .id(ID)
            .description(description)
            .title("title")
            .author(member)
            .build();

    private Answer answer = Answer.builder()
            .id(ID)
            .answerText("answer_text")
            .creationDate(LocalDateTime.now())
            .author(member)
            .question(question)
            .photos(new ArrayList<>())
            .comments(new ArrayList<>())
            .build();

    private Tag tag = new Tag(ID, "tag_name", "tag_description",
            new ArrayList<>(), 1, 2);


    @BeforeEach
    public void setUp() {
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        tagRepository = mock(TagRepository.class);

        questionService = new QuestionServiceImpl(answerRepository, questionRepository, tagRepository);
    }

    @Test
    public void whenAddAnswerToOpenQuestionThenReturnNewAnswerWithId() {

        //when
        question.setStatus(QuestionStatus.OPEN);
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(answerRepository.save(answer)).thenReturn(answer);


        //then
        var resultAnswer = questionService.addAnswerToQuestion(answer);

        assertThat(resultAnswer.getId()).isEqualTo(ID);
        assertThat(question.getAnswerList()).contains(resultAnswer);


        //verify

        verify(questionRepository).findById(1L);
        verify(questionRepository).update(question);

        verify(answerRepository).save(answer);

        verifyNoMoreInteractions(questionRepository, answerRepository);
    }


    @Test
    public void whenAddAnswerToNotOpenQuestionThenExceptionThrown() {
        //when
        question.setStatus(QuestionStatus.CLOSE);
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));


        //then
        assertThatThrownBy(() -> questionService.addAnswerToQuestion(answer))
                .isInstanceOf(QuestionClosedException.class);


        //verify
        verify(questionRepository).findById(ID);

        verifyNoMoreInteractions(questionRepository);
    }


    @Test
    public void shouldThrowExceptionWhenAnswerIsNull() {

        //then
        assertThatThrownBy(() -> questionService.addAnswerToQuestion(null))
                .isInstanceOf(NullPointerException.class);
    }


    @Test
    public void whenAddTagToQuestionThenReturnTrue() {

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(tagRepository.findById(ID)).thenReturn(Optional.of(tag));


        //then
        assertThat(questionService.addTagToQuestion(tag, question)).isTrue();


        //verify
        verify(questionRepository).findById(ID);
        verify(questionRepository).update(question);

        verify(tagRepository).findById(ID);

        verifyNoMoreInteractions(questionRepository, tagRepository);
    }


    @Test
    public void shouldThrowExceptionIfQuestionNotFound() {

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.empty());


        //then
        assertThatThrownBy(()-> questionService.addTagToQuestion(tag, question))
                .isInstanceOf(QuestionNotFoundException.class);

        assertThatThrownBy(()-> questionService.addAnswerToQuestion(answer))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    public void ifTagNotFoundInDBShouldSaveTagInDB() {

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(tagRepository.findById(ID)).thenReturn(Optional.empty());


        //then
        assertThat(questionService.addTagToQuestion(tag, question)).isTrue();
        assertThat(question.getTagList().contains(tag));


        //verify
        verify(questionRepository).findById(ID);
        verify(questionRepository).update(question);

        verify(tagRepository).findById(ID);
        verify(tagRepository).save(tag);

        verifyNoMoreInteractions(questionRepository, tagRepository);
    }


    @Test
    public void ifTagAlreadyAddedToQuestionThrowException() {

        //when
        question.getTagList().add(tag);

        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(tagRepository.findById(ID)).thenReturn(Optional.of(tag));


        //then
        assertThat(question.getTagList()).contains(tag);
        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, question))
                .isInstanceOf(TagAlreadyAddedException.class);

        //verify
        verify(questionRepository).findById(ID);

        verifyNoMoreInteractions(questionRepository);
    }


    @Test
    public void addTagToQuestionMethodShouldThrowExceptionWhenTagIsNull() {

        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion( null, question))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("tag is marked @NonNull but is null");
    }

    @Test
    public void addTagToQuestionMethodShouldThrowExceptionWhenQuestionIsNull() {

        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("question is marked @NonNull but is null");
    }
}
