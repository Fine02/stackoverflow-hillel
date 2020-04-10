package com.ra.course.com.stackoverflow.service.question;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.*;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
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
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private TagRepository tagRepository;
    private Account account = createNewAccount();
    private Member member = createNewMember(ID, account);
    private Question question = createNewQuestion(ID, member);
    private Answer answer = createNewAnswer(ID, member, question);
    private Tag tag = createNewTag(ID);


    @BeforeEach
    public void setUp() {
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        tagRepository = mock(TagRepository.class);

        questionService = new QuestionServiceImpl(answerRepository, questionRepository, tagRepository);
    }

    @Test
    public void whenAddAnswerToOpenQuestionThenReturnNewAnswerWithId() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(answerRepository.save(answer)).thenReturn(answer);

        //then
        var resultAnswer = questionService.addAnswerToQuestion(answer);

        assertThat(resultAnswer.getId()).isEqualTo(ID);
        assertThat(question.getAnswerList()).contains(resultAnswer);
    }


    @Test
    public void whenAddAnswerToNotOpenQuestionThenExceptionThrown() {
        //given
        question.setStatus(QuestionStatus.CLOSE);

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));

        //then
        assertThatThrownBy(() -> questionService.addAnswerToQuestion(answer))
                .isInstanceOf(QuestionClosedException.class);
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
    }


    @Test
    public void ifTagAlreadyAddedToQuestionThrowException() {
        //given
        question.getTagList().add(tag);

        //when
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));
        when(tagRepository.findById(ID)).thenReturn(Optional.of(tag));

        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, question))
                .isInstanceOf(TagAlreadyAddedException.class);
    }


    @Test //'addTagToQuestion' it's name of method
    public void addTagToQuestionMethodShouldThrowExceptionWhenTagIsNull() {
        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion( null, question))
                .isInstanceOf(NullPointerException.class);
    }

    @Test //'addTagToQuestion' it's name of method
    public void addTagToQuestionMethodShouldThrowExceptionWhenQuestionIsNull() {
        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, null))
                .isInstanceOf(NullPointerException.class);
    }


    private Account createNewAccount() {
        return Account.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();
    }

    private Member createNewMember(long id, Account account) {
        return Member.builder()
                .id(id)
                .account(account)
                .build();
    }

    private Question createNewQuestion(long id, Member member) {
        return Question.builder()
                .id(id)
                .description("some_question")
                .title("title")
                .author(member)
                .build();
    }

    private Answer createNewAnswer(long id, Member member, Question question) {
        return Answer.builder()
                .id(id)
                .answerText("answer_text")
                .creationDate(LocalDateTime.now())
                .author(member)
                .question(question)
                .photos(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private Tag createNewTag(long id) {
        return new Tag(id, "tag_name", "tag_description");
    }
}
