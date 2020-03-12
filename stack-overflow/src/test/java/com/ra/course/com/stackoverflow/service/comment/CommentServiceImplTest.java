package com.ra.course.com.stackoverflow.service.comment;

import com.ra.course.com.stackoverflow.entity.*;

import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;

import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class CommentServiceImplTest {
    private CommentServiceImpl commentService;
    private CommentRepository commentRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private final long ID = 1L;
    private String description = "some description";

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

    private Comment comment = Comment.builder()
            .id(ID)
            .text("Some_comment")
            .creationDate(LocalDateTime.now())
            .author(member)
            .commentable(question)
            .build();


    @BeforeEach
    public void setUp() {
        commentRepository = mock(CommentRepository.class);
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);

        commentService = new CommentServiceImpl(commentRepository, questionRepository, answerRepository);
    }


    @Test
    public void whenAddCommentToQuestionThenReturnNewCommentWithId() {
        //when
        question.setStatus(QuestionStatus.OPEN);

        when(commentRepository.save(comment)).thenReturn(comment);
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));


        //then
        var resultComment = commentService.addCommentToQuestion(comment, question);

        assertThat(resultComment.getId()).isEqualTo(ID);
        assertThat(question.getCommentList()).contains(resultComment);


        //verify
        verify(commentRepository).save(comment);

        verify(questionRepository).findById(ID);
        verify(questionRepository).update(question);

        verifyNoMoreInteractions(commentRepository, questionRepository);
    }


    @Test
    public void whenAddCommentToAnswerThenReturnNewCommentWithId() {

        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(answerRepository.findById(ID)).thenReturn(Optional.of(answer));

        //then
        var resultComment = commentService.addCommentToAnswer(comment, answer);

        assertThat(resultComment.getId()).isEqualTo(ID);
        assertThat(answer.getComments()).contains(resultComment);

        //verify
        verify(commentRepository).save(resultComment);

        verify(answerRepository).findById(ID);
        verify(answerRepository).update(answer);

        verifyNoMoreInteractions(commentRepository, answerRepository);
    }


    @Test
    public void shouldThrowExceptionWhenCommentIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToQuestion(null, question))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("comment is marked @NonNull but is null");

        assertThatThrownBy(() -> commentService.addCommentToAnswer(null, answer))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("comment is marked @NonNull but is null");
    }


    @Test
    public void shouldThrowExceptionWhenQuestionIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToQuestion(comment, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("question is marked @NonNull but is null");
    }


    @Test
    public void shouldThrowExceptionWhenAnswerIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToAnswer(comment, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("answer is marked @NonNull but is null");


    }

}
