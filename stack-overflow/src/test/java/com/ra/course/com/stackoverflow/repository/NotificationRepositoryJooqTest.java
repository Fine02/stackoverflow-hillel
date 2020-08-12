package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.NotificationRecord;
import com.ra.course.com.stackoverflow.repository.impl.NotificationRepositoryJooqImpl;
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
import java.util.List;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.NOTIFICATION_TABLE;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.getNotification;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationRepositoryJooqTest {

    private static NotificationRepository data;
    private Notification notification;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new NotificationMockProvider()), SQLDialect.H2);

        // Initialise CommentRepositoryJooqImpl with mocked DSL
        data = new NotificationRepositoryJooqImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        notification = getNotification();
        notification.setQuestion(ID);
        notification.setText("Your comment was upvoted");
    }

    @Test
    void whenGetByIdThenReturnOptionalOfNote() {
        //when
        var result = data.getById(ID);
        //then
        assertEquals(Optional.of(notification), result);
    }

    @Test
    void whenGetByIdAndItAbsentThenReturnOptionalEmpty() {
        //when
        var result = data.getById(123L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByMemberIdThenReturnListWithNote() {
        //given
        var note2 = getNotification();
        note2.setAnswer(ID);
        note2.setText("Your comment was upvoted");
        var note3 = getNotification();
        note3.setComment(ID);
        note3.setText("Your comment was upvoted");
        var expectedList = List.of(notification, note2, note3);
        //when
        var result = data.getAllByMemberId(555L);
        //then
        assertEquals(expectedList, result);
    }

    @Test
    void whenSaveNotificationWithQuestionIdThenReturnWithId() {
        //given
        notification.setId(null);
        //when
        var result = data.save(notification);
        //then
        assertNotNull(result.getId());
    }

    @Test
    void whenSaveNotificationWithAnswerIdThenReturnWithId() {
        //given
        notification.setId(null);
        notification.setAnswer(ID);
        notification.setQuestion(null);
        //when
        var result = data.save(notification);
        //then
        assertNotNull(result.getId());
    }

    @Test
    void whenSaveNotificationWithCommentIdThenReturnWithId() {
        //given
        notification.setId(null);
        notification.setComment(ID);
        notification.setQuestion(null);
        //when
        var result = data.save(notification);
        //then
        assertNotNull(result.getId());
    }

    @Test
    void whenUpdateThenGetUpdatedNotification() {
        //given
        notification.setId(777L);
        notification.setRead(true);
        //when
        data.updateRead(notification);
        var result = data.getById(777L);
        //then
        assertEquals(Optional.of(notification), result);
    }
}

class NotificationMockProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) {

        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();

        //Results for mock
        var result = dslContext.newResult(NOTIFICATION_TABLE);

        var recordFalseQuestionId = new NotificationRecord(ID, Timestamp.valueOf(LocalDateTime.MIN), String.join(",", List.of(ID.toString(), ID.toString(), String.valueOf(0), String.valueOf(0), "Your comment was upvoted", String.valueOf(false))));
        var recordFalseAnswerId = new NotificationRecord(555L, Timestamp.valueOf(LocalDateTime.MIN), String.join(",", List.of(ID.toString(), String.valueOf(0), ID.toString(), String.valueOf(0), "Your comment was upvoted", String.valueOf(false))));
        var recordFalseCommentId = new NotificationRecord(666L, Timestamp.valueOf(LocalDateTime.MIN), String.join(",", List.of(ID.toString(), String.valueOf(0), String.valueOf(0), ID.toString(), "Your comment was upvoted", String.valueOf(false))));
        var recordTrue = new NotificationRecord(777L, Timestamp.valueOf(LocalDateTime.MIN), String.join(",", List.of(ID.toString(), ID.toString(), String.valueOf(0), String.valueOf(0), "Your comment was upvoted", String.valueOf(true))));

        //Stipulations for returning different results
        if ((sql.startsWith("SELECT") && bindings[0].equals(ID)) ||
                (sql.startsWith("SELECT") && bindings[0].equals(String.valueOf(ID)))) {
            result.add(recordFalseQuestionId);
            mock[0] = new MockResult(1, result);

        } else if (sql.startsWith("SELECT") && bindings[0].equals(String.valueOf(555L))) {
            result.add(recordFalseQuestionId);
            result.add(recordFalseAnswerId);
            result.add(recordFalseCommentId);
            mock[0] = new MockResult(1, result);

        } else if (sql.startsWith("SELECT") && bindings[0].equals(777L)) {
            result.add(recordTrue);
            mock[0] = new MockResult(1, result);

        } else if (sql.startsWith("SELECT") && bindings[0].equals(555L)) {
            result.add(recordFalseAnswerId);
            mock[0] = new MockResult(1, result);

        } else if (sql.startsWith("SELECT") && bindings[0].equals(666L)) {
            result.add(recordFalseCommentId);
            mock[0] = new MockResult(1, result);

        } else if(sql.startsWith("UPDATE")){
            mock[0] = new MockResult(0, result);

        } else if (bindings.length > 1) {
            if (bindings[1].toString().startsWith("1,1,0,0")) {
                result.add(recordFalseQuestionId);
                mock[0] = new MockResult(1, result);

            } else if (bindings[1].toString().startsWith("1,0,1,0")) {
                result.add(recordFalseAnswerId);
                mock[0] = new MockResult(1, result);

            } else if (bindings[1].toString().startsWith("1,0,0,1")) {
                result.add(recordFalseCommentId);
                mock[0] = new MockResult(1, result);
            }
        } else {
            mock[0] = new MockResult(0, result);
        }

        return mock;
    }
}

