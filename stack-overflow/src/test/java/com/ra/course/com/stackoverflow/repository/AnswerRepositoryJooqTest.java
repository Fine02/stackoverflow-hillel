package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import com.ra.course.com.stackoverflow.repository.impl.AnswerRepositoryJooqImpl;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getAnswer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnswerRepositoryJooqTest {
    private Answer answer;
    private static AnswerRepository data;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new AnswerMockProvider()), SQLDialect.H2);

        //mock inner repositories
        var photoRepository = mock(PhotoRepository.class);
        var commentRepository = mock(CommentRepository.class);
        when(photoRepository.findByAnswerId(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findByAnswerId(anyLong())).thenReturn(new ArrayList<>());

        // Initialise AnswerRepository with mocked DSL
        data = new AnswerRepositoryJooqImpl(dslContext, commentRepository, photoRepository);
    }

    @BeforeEach
    void setUp() {
        answer = getAnswer();
    }

    @Test
    public void whenFindAnswerByIdAndAnswerPresentInDataBaseThenReturnAnswer() {
        //when
        var result = data.findById(ID);
        //then
        assertEquals(Optional.of(answer), result);
    }

    @Test
    public void whenFindAnswerByIdAndAnswerNotPresentInDataBaseThenReturnOptionalEmpty() {
        //when
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenSaveAnswerInDataBaseThenReturnAnswerWithId() {
        //given
        answer.setId(null);
        //when
        var result = data.save(answer);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenDeleteAnswerFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //given
        answer.setId(666L);
        //when
        data.delete(answer);
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenUpdateAnswerInDatabaseThenGetUpdatedAnswer() {
        //given
        answer.setId(777L);
        answer.setText("Test111");
        //when
        data.update(answer);
        var result = data.findById(777L);
        //then
        assertEquals(Optional.of(answer), result);
    }

    @Test
    public void whenFindAnswerByQuestionIdThenReturnList() {
        //when
        var listResult = data.findByQuestionId(ID);
        //then
        assertTrue(listResult.contains(answer));
    }


    @Test
    public void whenFindAnswerByMemberIdThenReturnList() {
        //when
        var listResult = data.findByMemberId(ID);
        //then
        assertTrue(listResult.contains(answer));
    }

    @Test
    public void whenFindBySomeIdButThereIsNotInDbThenReturnEmptyList() {
        //when
        var emptyList = new ArrayList<>();
        //then
        assertThat(emptyList)
                .isEqualTo(data.findByQuestionId(666L))
                .isEqualTo(data.findByMemberId(666L));
    }
}


    //implementation MockProvider for this test
    class AnswerMockProvider implements MockDataProvider {


        @Override
        public MockResult[] execute(MockExecuteContext ctx){

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            String sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            var result = create.newResult(ANSWER_TABLE);

            var answerRecordID1 = new AnswerRecord(ID, TEXT,
                    false, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    ID, ID);
            var answerRecordID777 = new AnswerRecord(777L, "Test111",
                    false, 0, 0, Timestamp.valueOf(LocalDateTime.now()),
                    ID, ID);

            //Stipulations for returning different results
            if (sql.startsWith("INSERT") || (sql.startsWith("SELECT \"PUBLIC\".\"ANSWER\"")&& value[0].equals(ID))) {
                result.add(answerRecordID1);
                mock[0] = new MockResult(1, result);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"ANSWER\"") && value[0].equals(777L)) {
                result.add(answerRecordID777);
                mock[0] = new MockResult(1, result);
            }else {
                mock[0] = new MockResult(0, result);
            }

            return mock;
        }
    }

