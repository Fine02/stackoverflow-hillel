package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.exception.service.QuestionClosedException;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.service.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class QuestionServiceIntegrationTest {
    private final long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);
    private Question openQuestion = createNewQuestion(ID, member);
    private Question closedQuestion = createNewQuestion(2L, member);
    private Answer answer = createNewAnswer(ID, member, openQuestion);
    private Tag tag = createNewTag(ID);

    @Autowired
    private QuestionService questionService;


    @Test
    public void whenAddAnswerToOpenQuestionThenQuestionContainsAnswerInList() {
        //given
        openQuestion.setStatus(QuestionStatus.OPEN);

        //when
        var result = questionService.addAnswerToQuestion(answer);

        //then
        assertThat(result.getAnswerList()).contains(answer);
    }

    @Test
    public void whenAddAnswerToClosedQuestionThenThrowException() {
        //given
        var answer = createNewAnswer(1L, member, closedQuestion);

        //then
        assertThatThrownBy(() -> questionService.addAnswerToQuestion(answer))
                .isInstanceOf(QuestionClosedException.class);
    }

    @Test
    public void whenAddTagToOpenQuestionThenReturnTrue() {
        //given
        openQuestion.setStatus(QuestionStatus.OPEN);

        //when
        var result = questionService.addTagToQuestion(tag, openQuestion);

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void whenAddTagToOpenQuestionButTagAlreadyAddedThenThrowException() {
        //given
        openQuestion.setStatus(QuestionStatus.OPEN);

        //when
        var result = questionService.addTagToQuestion(tag, openQuestion);

        //then
        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, openQuestion))
                .isInstanceOf(TagAlreadyAddedException.class);
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
                .description("some_question")
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

    private Tag createNewTag(long id) {
        return new Tag(id, "tag_name", "tag_description");
    }
}
