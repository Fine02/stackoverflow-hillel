package com.ra.course.com.stackoverflow.repository;


import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.jooq.enums.AccountStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedAnswerRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedCommentRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedQuestionRecord;
import com.ra.course.com.stackoverflow.repository.impl.*;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MemberRepositoryJooqTest {

    private static MemberRepository data;
    private Member member;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new MemberMockProvider()), SQLDialect.H2);


        //mock inner repositories
        var questionRepository = mock(QuestionRepository.class);
        var commentRepository = mock(CommentRepository.class);
        var answerRepository = mock(AnswerRepository.class);
        when(questionRepository.findByMemberId(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findByMemberId(anyLong())).thenReturn(new ArrayList<>());
        when(answerRepository.findByMemberId(anyLong())).thenReturn(new ArrayList<>());

        // Initialise repositories with mocked DSL
        data = new MemberRepositoryJooqImpl(dslContext, questionRepository, answerRepository, commentRepository);
    }

    @BeforeEach
    void setUp() {
        member = getMember();
    }

    @Test
    public void whenFindMemberByIdAndMemberPresentInDataBaseThenReturnMember() {
        //when
        var result = data.findById(ID);
        //then
        assertEquals(Optional.of(member), result);
    }

    @Test
    public void whenFindMemberByIdAndMemberNotPresentInDataBaseThenReturnOptionalEmpty() {
        //when
        var result = data.findById(555L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindMemberByEmailAndMemberPresentInDataBaseThenReturnMember() {
        //when
        var result = data.findByEmail(EMAIL);
        //then
        assertEquals(Optional.of(member), result);
    }

    @Test
    public void whenFindMemberByEmailAndMemberNotPresentInDataBaseThenReturnOptionalEmpty() {
        //when
        var result = data.findByEmail("some email");
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenSaveMemberInDataBaseThenReturnMemberWithId() {
        //given
        member.getAccount().setId(null);
        //when
        var result = data.save(member);
        //then
        assertNotNull(result.getAccount().getId());
    }

    @Test
    public void whenDeleteMemberFromDataBaseAndTryFindItThenReturnMemberWithStatusArchived() {
        //given
        member.getAccount().setId(666L);
        //when
        data.delete(member);
        var result = data.findById(666L);
        //then
        assertEquals(AccountStatus.ARCHIVED, result.get().getAccount().getStatus());
    }


    @Test
    public void whenUpdateMemberInDatabaseThenGetUpdatedMember() {
        //given
        member.getAccount().setId(777L);
        member.getAccount().setName("blah blah");
        member.getDownVotedAnswersId().add(2L);
        member.getUpVotedAnswersId().add(3L);
        member.getDownVotedCommentsId().add(2L);
        member.getUpVotedCommentsId().add(3L);
        member.getDownVotedQuestionsId().add(2L);
        member.getUpVotedQuestionsId().add(3L);
        //when
        data.update(member);
        var result = data.findById(777L).get();
        //then
        assertEquals(member, result);
        assertTrue(result.getDownVotedAnswersId().contains(2L) &&
                result.getUpVotedAnswersId().contains(3L) &&
                result.getDownVotedCommentsId().contains(2L) &&
                result.getUpVotedCommentsId().contains(3L) &&
                result.getDownVotedQuestionsId().contains(2L) &&
                result.getUpVotedQuestionsId().contains(3L));
    }

    @Test
    public void whenTryFindMemberByNameThenReturnListOfMember() {
        //when
        var result = data.findByMemberName(NAME);
        //then
        assertTrue(result.contains(member));
    }

    @Test
    public void whenTryFindMemberByNameAndMemberNotExistsThenReturnEmptyList() {
        //when
        var result = data.findByMemberName("John");
        //then
        assertTrue(result.isEmpty());
    }

}

//implementation MockProvider for this test
class MemberMockProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) {

        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        DSLContext create = DSL.using(SQLDialect.H2);
        MockResult[] mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        String sql = ctx.sql().toUpperCase();
        var value = ctx.bindings();

        //Results for mock
        var resultAccount = create.newResult(ACCOUNT_TABLE);

        //Objects for mocked result
        var accountRecord = new AccountRecord(ID, PASSWORD, NAME, EMAIL,
                0, AccountStatusType.active);
        var accountRecordID666 = new AccountRecord(666L, PASSWORD, NAME, EMAIL,
                0, AccountStatusType.archived);
        var accountRecordID777 = new AccountRecord(777L, PASSWORD, "blah blah", EMAIL,
                0, AccountStatusType.active);

        //Stipulations for returning different results
        if (sql.startsWith("INSERT") ||
                (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(ID)) ||
                (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(NAME)) ||
                (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(EMAIL))) {
            resultAccount.add(accountRecord);
            mock[0] = new MockResult(1, resultAccount);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(666L)) {
            resultAccount.add(accountRecordID666);
            mock[0] = new MockResult(1, resultAccount);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(777L)) {
            resultAccount.add(accountRecordID777);
            mock[0] = new MockResult(1, resultAccount);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"MEMBER_VOTED_ANSWER\"") && value[0].equals(777L)) {
            var votedAnswers = create.newResult(MEMBER_VOTED_ANSWER);
            votedAnswers.add(new MemberVotedAnswerRecord(777L, 2L, false));
            votedAnswers.add(new MemberVotedAnswerRecord(777L, 3L, true));
            mock[0] = new MockResult(1, votedAnswers);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"MEMBER_VOTED_COMMENT\"") && value[0].equals(777L)) {
            var votedComments = create.newResult(MEMBER_VOTED_COMMENT);
            votedComments.add(new MemberVotedCommentRecord(777L, 2L, false));
            votedComments.add(new MemberVotedCommentRecord(777L, 3L, true));
            mock[0] = new MockResult(1, votedComments);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"MEMBER_VOTED_QUESTION\"") && value[0].equals(777L)) {
            var votedQuestions = create.newResult(MEMBER_VOTED_QUESTION);
            votedQuestions.add(new MemberVotedQuestionRecord(777L, 2L, false));
            votedQuestions.add(new MemberVotedQuestionRecord(777L, 3L, true));
            mock[0] = new MockResult(1, votedQuestions);

        } else {
            mock[0] = new MockResult(0, resultAccount);
        }

        return mock;
    }
}
