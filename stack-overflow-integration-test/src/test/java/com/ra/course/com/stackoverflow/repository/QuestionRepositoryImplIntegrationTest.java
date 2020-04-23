package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.repository.impl.QuestionRepositoryImpl;
import com.ra.course.com.stackoverflow.repository.impl.TagRepositoryImpl;
import com.ra.course.com.stackoverflow.service.question.QuestionService;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
@ActiveProfiles("test")
public class QuestionRepositoryImplIntegrationTest {
    private long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);
    private Question question = createNewQuestion(ID, member);
    private Tag tag = new Tag(1L, "JAVA", "Some tag description");


    @Autowired
    private QuestionRepositoryImpl questionRepository;
    @Autowired
    private TagRepositoryImpl tagRepository;
    @Autowired
    private DSLContext dslContext;
    @Autowired
    private QuestionService questionService;


    @Test
    public void whenFindQuestionByIdAndQuestionPresentInDataBaseThenReturnQuestion() {
        var question = questionRepository.findById(2L).get();

        assertEquals(question.getId(), 2L);
    }

    @Test
    public void whenFindQuestionByIdAndQuestionNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Question> question = questionRepository.findById(666L);

        assertThat(question.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveQuestionInDataBaseThenReturnQuestionWithId() {
        var savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion.getId() > 0).isTrue();
    }

    @Test
    public void whenDeleteQuestionFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //it's necessary only for deletion tag from db for this test!!!
        dslContext.execute("SET FOREIGN_KEY_CHECKS = 0;");
        questionRepository.delete(question);

        Optional<Question> result = questionRepository.findById(question.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenUpdateQuestionInDatabaseThenGetUpdatedQuestion() {
        var before = questionRepository.findById(2L).get();
        before.setTitle("Test111");
        questionRepository.update(before);
        var after = questionRepository.findById(2L).get();

        assertThat(after.getTitle().equals("Test111")).isTrue();
    }

    @Test
    public void whenFindQuestionByMemberIdThenReturnListOfQuestion() {
        var tmpMember = createNewMember(account);
        var findingQuestion = createNewQuestion(2L, tmpMember);


        var result = questionRepository.findByMemberId(2L);

        assertTrue(result.contains(findingQuestion));
    }

    @Test
    public void whenFindQuestionByTagThenReturnListOfQuestion() {
        questionService.addTagToQuestion(tag, question);

        var result = questionRepository.findByTag(tag);

        assertTrue(result.contains(question));
    }

    @Test
    public void whenFindQuestionByTitleThenReturnListOfQuestion() {
        var result = questionRepository.findByTitle("title");

        for (Question q : result) {
            assertTrue(q.getTitle().contains("title"));

        }

    }

    @Test
    public void whenQuestionFindByTitleAndTagThenReturnListOfQuestion() {
        var result =  questionRepository.findByTitleAndTag( "title", new Tag(3L, "SQL", "Some tag description"));

        assertTrue(result.size() > 0);
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
                .title("title")
                .description("some_question")
                .viewCount(10)
                .voteCount(1)
                .creationTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .status(QuestionStatus.OPEN)
                .closingRemark(QuestionClosingRemark.NOT_MARKED_FOR_CLOSING)
                .authorId(member.getAccount().getId())
                .bounty(Optional.of(new Bounty(1L, 10, LocalDateTime.MAX, member.getAccount().getId())))
                .commentList(new ArrayList<>())
                .answerList(new ArrayList<>())
                .photoList(new ArrayList<>())
                .tagList(new ArrayList<>())
                .membersIdsWhoVotedQuestionToClose(new HashMap<>())
                .build();
    }
}
