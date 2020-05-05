package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.service.comment.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class CommentServiceIntegrationTest {
    private final long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);
    private Question question = createNewQuestion(ID, member);
    private Answer answer = createNewAnswer(ID, member, question);
    private Comment comment = createNewComment(ID, member, question);

    @Autowired
    private CommentService commentService;


    @Test
    public void whenAddCommentToOpenQuestionThenQuestionContainsCommentInList() {
        //given
        question.setStatus(QuestionStatus.OPEN);

        //when
        var resultQuestion = commentService.addCommentToQuestion(comment, question);

        //then
        assertThat(resultQuestion.getCommentList()).contains(comment);
    }

    @Test
    public void whenAddCommentToAnswerThenAnswerContainsCommentInList() {

        //when
        var resultAnswer = commentService.addCommentToAnswer(comment, answer);

        //then
        assertThat(resultAnswer.getComments()).contains(comment);
    }

    private Account createNewAccount(long id) {
        return Account.builder()
                .id(id)
                .password("password")
                .email("email")
                .name("name")
                .build();
    }

    private Member createNewMember(Account account) {
        return Member.builder()
                .account(account)
                .build();
    }

    private Question createNewQuestion(long id, Member member) {
        return Question.builder()
                .id(id)
                .description("some question")
                .title("title")
                .authorId(member.getAccount().getId())
                .build();
    }

    private Answer createNewAnswer(long id, Member member, Question question) {
        return Answer.builder()
                .id(id)
                .answerText("answer_text")
                .creationDate(LocalDateTime.now())
                .authorId(member.getAccount().getId())
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
                .authorId(member.getAccount().getId())
                .questionId(question.getId())
                .build();
    }
}
