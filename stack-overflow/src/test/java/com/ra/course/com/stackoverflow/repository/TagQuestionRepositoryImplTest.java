package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.TagQuestionRecord;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagQuestionTable.TAG_QUESTION_TABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TagQuestionRepositoryImplTest {
    private long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);
    private Question question = createNewQuestion(ID, member);
    private Tag tag = new Tag(ID, "JAVA", "JAVA Teg Description");

    // Initialise data provider
    private final MockDataProvider provider = new TagQuestionRepositoryImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise repositories with mocked DSL
    private final TagQuestionRepository tagQuestionRepository = new TagQuestionRepositoryImpl(dslContext);

    @Test
    public void whenSaveQuestionAndTagInDataBaseThenCheckTheyPresentInDB() {
        tagQuestionRepository.save(tag, question);

        //getting result from table
        var result = dslContext.fetchOne(TAG_QUESTION_TABLE, TAG_QUESTION_TABLE.QUESTION_ID.eq(1L));

        assertEquals(result.getQuestionId(), 1L);
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
                .bounty(Optional.of(new Bounty(1L, 10, LocalDateTime.now(), 1L)))
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

            //Results for mock
            var result = create.newResult(TAG_QUESTION_TABLE.TAG_ID, TAG_QUESTION_TABLE.QUESTION_ID);

            //Objects for mocked result
            var tagQuestionRecordID1 = new TagQuestionRecord(1L, 1L);

            //Stipulations for returning different results
            if(sql.startsWith("SELECT \"PUBLIC\".\"TAG_QUESTION\"")){
                result.add(tagQuestionRecordID1);

                mock[0] = new MockResult(1, result);
            }else {

                mock[0] = new MockResult(0, result);
            }

            return mock;
        }
    }
}
