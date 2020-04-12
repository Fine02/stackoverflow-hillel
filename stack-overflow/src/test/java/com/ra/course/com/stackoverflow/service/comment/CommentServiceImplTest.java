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
    private Account account = createNewAccount();
    private Member member = createNewMember(ID, account);
    private Question question = createNewQuestion(ID, member);
    private Answer answer = createNewAnswer(ID, member, question);
    private Comment comment = createNewComment(ID, member, question);


    @BeforeEach
    public void setUp() {
        commentRepository = mock(CommentRepository.class);
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);

        commentService = new CommentServiceImpl(commentRepository, questionRepository, answerRepository);
    }


    @Test
    public void whenAddCommentToOpenQuestionThenReturnUpdatedQuestion() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(questionRepository.findById(ID)).thenReturn(Optional.of(question));

        //then
        var resultQuestion = commentService.addCommentToQuestion(comment, question);
        assertThat(resultQuestion.getCommentList()).contains(comment);
    }


    @Test
    public void whenAddCommentToAnswerThenReturnUpdatedAnswer() {
        //when
        when(commentRepository.save(comment)).thenReturn(comment);
        when(answerRepository.findById(ID)).thenReturn(Optional.of(answer));

        //then
        var resultAnswer = commentService.addCommentToAnswer(comment, answer);
        assertThat(answer.getComments()).contains(comment);
    }


    @Test
    public void shouldThrowExceptionWhenCommentIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToQuestion(null, question))
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> commentService.addCommentToAnswer(null, answer))
                .isInstanceOf(NullPointerException.class);
    }


    @Test
    public void shouldThrowExceptionWhenQuestionIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToQuestion(comment, null))
                .isInstanceOf(NullPointerException.class);
    }


    @Test
    public void shouldThrowExceptionWhenAnswerIsNull() {

        //then
        assertThatThrownBy(() -> commentService.addCommentToAnswer(comment, null))
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
                .description("some question")
                .title("title")
                .author(member)
                .build();
    }

    private Answer createNewAnswer(long id, Member member, Question question) {
        return Answer.builder()
                .id(id)
                .answerText("answer_text")
                .creationDate(LocalDateTime.now())
                .authorId(member.getId())
                .questionId(question.getId())
                .photos(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private Comment createNewComment(long id, Member member, Question question) {
        return Comment.builder()
                .id(id)
                .text("Some_comment")
                .creationDate(LocalDateTime.now())
                .authorId(member.getId())
                .questionId(question.getId())
                .build();
    }
}
