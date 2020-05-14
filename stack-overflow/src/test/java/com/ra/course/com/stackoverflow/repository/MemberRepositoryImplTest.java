package com.ra.course.com.stackoverflow.repository;


import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.jooq.enums.AccountStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.com.stackoverflow.repository.impl.*;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.ACCOUNT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberRepositoryImplTest {
    private long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);

    // Initialise data provider
    private final MockDataProvider provider = new MemberRepositoryImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise repositories with mocked DSL
    private final CommentRepositoryImpl commentRepository = new CommentRepositoryImpl(dslContext);
    private final PhotoRepositoryImpl photoRepository = new PhotoRepositoryImpl(dslContext);
    private final BountyRepository bountyRepository = new BountyRepositoryImpl(dslContext);
    private final TagRepository tagRepository = new TagRepositoryImpl(dslContext);
    private final AnswerRepository answerRepository = new AnswerRepositoryImpl(dslContext, commentRepository, photoRepository);
    private final QuestionRepository questionRepository = new QuestionRepositoryImpl(dslContext, bountyRepository, commentRepository, answerRepository,
            photoRepository, tagRepository);
    private final MemberRepository memberRepository = new MemberRepositoryImpl(dslContext, questionRepository, answerRepository,commentRepository);

    @Test
    public void whenFindMemberByIdAndMemberPresentInDataBaseThenReturnMember() {
        var result = memberRepository.findById(1L).get();

        assertEquals(result.getAccount().getId(), 1L);
    }

    @Test
    public void whenFindMemberByIdAndMemberNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Member> member = memberRepository.findById(-1L);

        assertThat(member.isEmpty()).isTrue();
    }
    @Test
    public void whenFindMemberByEmailAndMemberPresentInDataBaseThenReturnMember() {
        var result = memberRepository.findByEmail("email1@gmail.com").get();

        assertEquals(result.getAccount().getEmail(), "email1@gmail.com");
    }

    @Test
    public void whenFindMemberByEmailAndMemberNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Member> member = memberRepository.findByEmail("some email");

        assertThat(member.isEmpty()).isTrue();
    }

    @Test
    public void whenSaveMemberInDataBaseThenReturnMemberWithId() {
        var savedMember = memberRepository.save(member);

        assertThat(savedMember.getAccount().getId() > 0).isTrue();
    }

    @Test
    public void whenTrySaveNullThenThrowException() {
        assertThatThrownBy(() -> memberRepository.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenDeleteMemberFromDataBaseAndTryFindItThenReturnMemberWithStatusArchived() {
        var memberForDeleting = createNewMember( createNewAccount(666L));
        memberRepository.delete(memberForDeleting);

        Member result = memberRepository.findById(memberForDeleting.getAccount().getId()).get();

        assertEquals(AccountStatus.ARCHIVED, result.getAccount().getStatus());
    }

    @Test
    public void whenTryDeleteNullThenThrowException() {
        assertThatThrownBy(() -> memberRepository.delete(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenUpdateMemberInDatabaseThenGetUpdatedMember() {
        var before = createNewMember(createNewAccount(777L));
        memberRepository.save(before);

        before.getAccount().setName("blah blah");
        memberRepository.update(before);
        var after = memberRepository.findById(777L).get();

        assertEquals("blah blah", after.getAccount().getName());
    }

    @Test
    public void whenTryUpdateNullThenThrowException() {
        assertThatThrownBy(() -> memberRepository.update(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryFindMemberByNameAndMemberExistsThenReturnListOfMember() {
        var result = memberRepository.findByMemberName("John");

        assertThat(result.size() > 0).isTrue();
    }

    @Test
    public void whenTryFindMemberByNameAndMemberNotExistsThenReturnOptionalEmpty() {
        var result = memberRepository.findByMemberName("James Gosling");

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenTryFindNullByNameThenThrowException() {
        assertThatThrownBy(() -> memberRepository.findByMemberName(null))
                .isInstanceOf(NullPointerException.class);
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

    //implementation MockProvider for this test
    class MockProvider implements MockDataProvider {


        @Override
        public MockResult[] execute(MockExecuteContext ctx) throws SQLException {

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            String sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            var resultAccount = create.newResult(ACCOUNT_TABLE.ID, ACCOUNT_TABLE.PASSWORD, ACCOUNT_TABLE.NAME,
                    ACCOUNT_TABLE.EMAIL, ACCOUNT_TABLE.REPUTATION, ACCOUNT_TABLE.ACCOUNT_STATUS);

            //Objects for mocked result
            var accountRecordID1 = new AccountRecord(1L, "password1", "John", "email1@gmail.com",
                    2, AccountStatusType.active);
            var accountRecordID666 = new AccountRecord(666L, "password1", "name1", "email1@gmail.com",
                    2, AccountStatusType.archived);
            var accountRecordID777 = new AccountRecord(777L, "password1", "blah blah", "email1@gmail.com",
                    2, AccountStatusType.archived);

            //Stipulations for returning different results
            if(sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value.length == 0){
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if (sql.startsWith("INSERT") || (sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals(1L))
            ||(sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"") && value[0].equals("email1@gmail.com"))) {
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else if(sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"")&& value[0].equals(666L)){
                resultAccount.add(accountRecordID666);

                mock[0] = new MockResult(1, resultAccount);
            }else if(sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"")&& value[0].equals(777L)){
                resultAccount.add(accountRecordID777);

                mock[0] = new MockResult(1, resultAccount);
            }else if(sql.startsWith("SELECT \"PUBLIC\".\"ACCOUNT\"")&& value[0].equals("John")){
                resultAccount.add(accountRecordID1);

                mock[0] = new MockResult(1, resultAccount);
            }else {

                mock[0] = new MockResult(0, resultAccount);
            }

            return mock;
        }
    }
}