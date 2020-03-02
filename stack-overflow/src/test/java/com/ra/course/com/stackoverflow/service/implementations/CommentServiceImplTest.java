package com.ra.course.com.stackoverflow.service.implementations;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.repository.interfaces.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.CommentRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentServiceImplTest {
    private CommentServiceImpl commentService;
    private CommentRepository commentRepository;
    private long id = 1L;
    private String description = "some description";

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

    private Comment comment = Comment.builder()
            .id(id)
            .text("Some_comment")
            .creationDate(LocalDateTime.now())
            .author(member)
            .commentable(question)
            .build();


    @BeforeEach
    public void setUp() {
        commentRepository = mock(CommentRepository.class);
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        AnswerRepository answerRepository = mock(AnswerRepository.class);
        commentService = new CommentServiceImpl(commentRepository, questionRepository, answerRepository);
    }


    @Test
    public void whenAddCommentToQuestionThatNotExistThenReturnNewCommentWithId() {
        question.setStatus(QuestionStatus.OPEN);

        //when
        when(commentRepository.save(comment)).thenReturn(comment);

        //then
        var resultComment = commentService.addCommentToQuestion(comment, question);
        assertThat(resultComment.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldThrowExceptionWhenCommentIsNull() {

        assertThatThrownBy(() -> commentService.addCommentToQuestion(null, question))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("comment is marked @NonNull but is null");
    }


    @Test
    public void whenAddCommentToAnswerThatNotExistThenReturnNewCommentWithId() {

        //when
        when(commentRepository.save(comment)).thenReturn(comment);

        //then
        var resultComment = commentService.addCommentToAnswer(comment, answer);
        assertThat(resultComment.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldThrowExceptionWhenQuestionIsNull() {

        assertThatThrownBy(() -> commentService.addCommentToQuestion(comment, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("question is marked @NonNull but is null");
    }

    @Test
    public void shouldThrowExceptionWhenAnswerIsNull() {

        assertThatThrownBy(() -> commentService.addCommentToAnswer(comment, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("answer is marked @NonNull but is null");
    }
}
