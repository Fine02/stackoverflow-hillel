package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
import com.ra.course.com.stackoverflow.repository.impl.CommentRepositoryJooqImpl;
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

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.COMMENT_TABLE;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CommentRepositoryJooqTest {

    private static CommentRepository data;

    private Comment comment;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new CommentMockProvider()), SQLDialect.H2);

        // Initialise CommentRepositoryJooqImpl with mocked DSL
        data = new CommentRepositoryJooqImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        comment = getComment();
    }

    @Test
    public void whenCommentSaveToDBThenReturnCommentWithId() {
        //given
        comment.setId(null);
        //when
        var result = data.save(comment);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenFindCommentByIdThenReturnOptionalOfComment() {
        //when
        var result = data.findById(ID);
        //then
        assertEquals(Optional.of(comment), result);
    }

    @Test
    public void whenFindCommentByIdAndNoSuchCommentThenReturnOptionalEmpty() {
        //when
        var result = data.findById(555L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenCommentDelete() {
        //given
        comment.setId(555L);
        //when
        data.delete(comment);
        var result = data.findById(555L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenUpdateCommentThenCheckCommentInDb() {
        //given
        comment.setId(777L);
        comment.setText("new text");
        //when
        data.update(comment);
        var result = data.findById(777L);
        //then
        assertEquals(Optional.of(comment), result);
    }

    @Test
    public void whenFindByByQuestionIdThenReturnListWithComment() {
        //when
        var listResult = data.findByQuestionId(ID);
        //then
        assertTrue(listResult.contains(comment));
    }

    @Test
    public void whenFindByByAnswerIdThenReturnListWithComment() {
        //when
        var listResult = data.findByAnswerId(ID);
        //then
        assertTrue(listResult.contains(comment));
    }

    @Test
    public void whenFindByByMemberIdThenReturnListWithComment() {
        //when
        var listResult = data.findByMemberId(ID);
        //then
        assertTrue(listResult.contains(comment));
    }

    @Test
    public void whenFindBySomeIdButThereIsNotInDbThenReturnEmptyList() {
        //when
        var emptyList = new ArrayList<>();
        //then
        assertThat(emptyList)
                .isEqualTo(data.findByAnswerId(666L))
                .isEqualTo(data.findByQuestionId(666L))
                .isEqualTo(data.findByMemberId(666L));
    }

    @Test
    public void whenGetAllThenReturnListWithComment() {
        //when
        var listResult = data.findAll();
        //then
        assertTrue(listResult.contains(comment));
    }

}
class CommentMockProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) {

        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();

        //Results for mock
        var result = dslContext.newResult(COMMENT_TABLE);

        var record1 = new CommentRecord(ID, TEXT, Timestamp.valueOf(LocalDateTime.MIN), 0, ID, null, null);
        var record2 = new CommentRecord(777L, "new text", Timestamp.valueOf(LocalDateTime.MIN), 0, ID, null, null);

        //Stipulations for returning different results
        if (sql.startsWith("INSERT") ||
                (sql.startsWith("SELECT") && bindings.length == 0) ||
                (sql.startsWith("SELECT") && bindings[0].equals(ID))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else if (sql.startsWith("SELECT") && bindings[0].equals(777L)) {
            result.add(record2);
            mock[0] = new MockResult(1, result);
        } else{
            mock[0] = new MockResult(0, result);
        }

        return mock;
    }
}
