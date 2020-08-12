package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Photo;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.PhotoRecord;
import com.ra.course.com.stackoverflow.repository.impl.PhotoRepositoryJooqImpl;
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

import static com.ra.course.com.stackoverflow.entity.jooq.tables.PhotoTable.PHOTO_TABLE;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class PhotoRepositoryTest {
    private static PhotoRepository data;
    private Photo photo;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new PhotoMockProvider()), SQLDialect.H2);

        // Initialise PhotoRepository with mocked DSL
        data = new PhotoRepositoryJooqImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        photo = new Photo(ID, "path", LocalDateTime.MIN, ID, ID);
    }

    @Test
    public void shouldSavePhoto() {
        //given
        photo.setId(null);
        //when
        var result = data.save(photo);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void shouldFindById() {
        //when
        var result =  data.findById(ID);
        //then
        assertEquals(Optional.of(photo), result);
    }

    @Test
    public void shouldReturnEmptyIfBountyNotFound() {
        //when
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldDeleteBounty() {
        //given
        photo.setId(666L);
        //when
        data.deleteById(666L);
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldGetListOfAnswersId() {
        //when
        var result = data.findByAnswerId(ID);
        //then
        assertEquals(List.of(photo), result);
    }

    @Test
    public void shouldGetListOfPhotoByQuestionId() {
        //when
        var result = data.findByQuestionId(ID);
        //then
        assertEquals(List.of(photo), result);
    }

    @Test
    public void shouldReturnEmptyList(){
            //when
            var emptyList = new ArrayList<>();
            //then
            assertThat(emptyList)
                    .isEqualTo(data.findByAnswerId(666L))
                    .isEqualTo(data.findByQuestionId(666L));
    }
}

class PhotoMockProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        
        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();

        //Results for mock
        var result = dslContext.newResult(PHOTO_TABLE);
        
        var record1 = new PhotoRecord(ID, "path", Timestamp.valueOf(LocalDateTime.MIN), ID, ID);
        
        if (sql.startsWith("INSERT") || (sql.startsWith("SELECT") && bindings[0].equals(ID))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else {
            mock[0] = new MockResult(0, result);
        }

        return mock;
    }
}
