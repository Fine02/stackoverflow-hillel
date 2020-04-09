package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
import com.ra.course.com.stackoverflow.repository.impl.CommentRepositoryImpl;
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
import java.util.List;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.COMMENT_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CommentRepositoryTest {
    private static CommentRepository data;

    private Comment comment;
    private Comment comment2;
    private List<Comment> expectedList;

    @BeforeAll
    static void beforeAll() {
        var provider = new MyProvider();
        var connection = new MockConnection(provider);
        var dslContext = DSL.using(connection, SQLDialect.H2);
        data = new CommentRepositoryImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        comment = createComment(1L,"Some comment text", 3);
        comment2 = createComment(777L, "new text", 123);
        expectedList = new ArrayList<>();
    }

    @Test
    public void whenCommentSaveToDBThenReturnCommentWithId() {
        var comment = Comment.builder().creationDate(LocalDateTime.now()).text("text").authorId(1L).questionId(2L).build();
        var newComment = data.save(comment);
        assertNotEquals(0, newComment.getId());
    }

    @Test
    public void whenFindCommentByIdThenReturnOptionalOfComment() {
        var expectedOptional = Optional.of(comment);
        assertEquals(expectedOptional, data.findById(1L));
    }

    @Test
    public void whenFindCommentByIdAndNoSuchCommentThenReturnOptionalEmpty() {
        assertEquals(Optional.empty(), data.findById(555L));
    }

    @Test
    public void whenCommentDelete() {
        data.delete(comment);
        assertEquals(Optional.empty(), data.findById(555L));
    }

    @Test
    public void whenUpdateCommentThenCheckCommentInDb() {
        comment.setVoteCount(123);
        comment.setText("new text");
        data.update(comment);
        assertEquals(comment2, data.findById(777L).get());
    }

    @Test
    public void whenFindByAnyIdThenReturnListWithComment() {
        expectedList.add(comment);
        expectedList.add(comment2);
        assertEquals(expectedList, data.findByQuestionId(888L));
        assertEquals(expectedList, data.findByMemberId(888L));
        assertEquals(expectedList, data.findByMemberId(888L));
    }

    @Test
    public void whenFindBySomeIdButThereIsNotInDbThenReturnEmptyList() {
        assertThat(expectedList)
                .isEqualTo(data.findByAnswerId(666L))
                .isEqualTo(data.findByQuestionId(666L))
                .isEqualTo(data.findByMemberId(666L));
    }
    private Comment createComment(Long id, String text, int voteCount){
        return Comment.builder()
                .id(id)
                .text(text)
                .creationDate(LocalDateTime.MIN)
                .voteCount(voteCount)
                .authorId(1L)
                .answerId(1L)
                .questionId(1L).build();

    }
}

class MyProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();
        var result = dslContext.newResult(COMMENT_TABLE);
        var record1 = new CommentRecord(1L, "Some comment text", Timestamp.valueOf(LocalDateTime.MIN), 3, 1L, 1L, 1L);
        var record2 = new CommentRecord(777L, "new text", Timestamp.valueOf(LocalDateTime.MIN), 123, 1L, 1L, 1L);
        if (sql.startsWith("INSERT") || (sql.startsWith("SELECT") && bindings[0].equals(1L))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else if (sql.startsWith("DELETE") ||
                sql.startsWith("UPDATE") ||
                (sql.startsWith("SELECT") && bindings[0].equals(555L)) ||
                (sql.startsWith("SELECT") && bindings[0].equals(666L))) {
            mock[0] = new MockResult(0, result);
        } else if (sql.startsWith("SELECT") && bindings[0].equals(777L)) {
            result.add(record2);
            mock[0] = new MockResult(1, result);
        } else if (sql.startsWith("SELECT") && bindings[0].equals(888L)) {
            result.add(record1);
            result.add(record2);
            mock[0] = new MockResult(1, result);
        }
        return mock;
    }
}
