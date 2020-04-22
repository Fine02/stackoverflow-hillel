package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.repository.impl.AnswerRepositoryImpl;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
@ActiveProfiles("test")
public class AnswerRepositoryImplIntegrationTest {
    private long ID = 1L;
    private Account account = createNewAccount();
    private Member member = createNewMember(ID, account);
    private Question question = createNewQuestion(ID, member);
    private Answer answer = createNewAnswer(ID, member, question);
    @Autowired
    private AnswerRepositoryImpl answerRepository;
    @Autowired
    private DSLContext dslContext;


    @Test
    public void whenFindAnswerByIdAndAnswerPresentInDataBaseThenReturnAnswer() {
        var answer = answerRepository.findById(2L).get();

        assertEquals(answer.getId(), 2L);
    }

    @Test
    public void whenFindAnswerByIdAndAnswerNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Answer> answer = answerRepository.findById(666L);

        assertThat(answer.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveAnswerInDataBaseThenReturnAnswerWithId() {
        var savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer.getId() > 0).isTrue();
    }

    @Test
    public void whenDeleteAnswerFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //it's necessary only for deletion tag from db for this test!!!
        dslContext.execute("SET FOREIGN_KEY_CHECKS = 0;");
        answerRepository.delete(answer);

        Optional<Answer> result = answerRepository.findById(answer.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenUpdateAnswerInDatabaseThenGetUpdatedAnswer() {
        var before = answerRepository.findById(2L).get();
        before.setAnswerText("Test111");
        answerRepository.update(before);
        var after = answerRepository.findById(2L).get();

        assertThat(after.getAnswerText().equals("Test111")).isTrue();
    }

    @Test
    public void whenFindAnswerByQuestionIdThenReturnList() {
        List<Answer> listResult = answerRepository.findByQuestionId(2L);

        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenFindAnswerByMemberIdThenReturnList() {
        List<Answer> listResult = answerRepository.findByMemberId(2L);

        assertThat(listResult.size() > 0).isTrue();
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
                .authorId(member.getId())
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
}
