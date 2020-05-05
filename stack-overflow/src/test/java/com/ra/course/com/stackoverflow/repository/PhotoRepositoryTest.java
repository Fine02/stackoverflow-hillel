package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Photo;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.PhotoRecord;
import com.ra.course.com.stackoverflow.repository.impl.PhotoRepositoryImpl;
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

import static com.ra.course.com.stackoverflow.entity.jooq.tables.PhotoTable.PHOTO_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PhotoRepositoryTest {
    private static PhotoRepository photoRepository;
    private Photo photo;

    @BeforeAll
    static void beforeAll() {
        var provider = new PhotoProvider();
        var connection = new MockConnection(provider);
        var dslContext = DSL.using(connection, SQLDialect.H2);
        photoRepository = new PhotoRepositoryImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        photo = new Photo(1L, "path", LocalDateTime.MIN, 3L, 3L);
    }

    @Test
    public void shouldSavePhoto() {
        var photoFromDb = photoRepository.save(photo);

        assertThat(photoFromDb.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindById() {
        var photoFromDb = photoRepository.findById(1L);

        assertThat(photoFromDb.get().getId()).isEqualTo(1);
    }

    @Test
    public void shouldReturnEmptyIfBountyNotFound() {
        assertThat(photoRepository.findById(2L)).isEmpty();
    }

    @Test
    public void shouldDeleteBounty() {
        assertThatCode(() -> photoRepository.deleteById(3L)).doesNotThrowAnyException();
    }

    @Test
    public void shouldGetListOfAnswersId() {
        assertThatCode(() -> photoRepository.findByAnswerId(photo.getAnswerId()))
                .doesNotThrowAnyException();
    }

    @Test
    public void shouldGetListOfPhotoByQuestionId() {
        assertThatCode(() -> photoRepository.findByQuestionId(photo.getQuestionId()))
                .doesNotThrowAnyException();
    }
}

class PhotoProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();
        var result = dslContext.newResult(PHOTO_TABLE);
        var record1 = new PhotoRecord(1L, "path", Timestamp.valueOf(LocalDateTime.MIN), 102L, 103L);
        if (sql.startsWith("INSERT") || (sql.startsWith("SELECT") && bindings[0].equals(1L))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else if (sql.startsWith("DELETE") ||
                   (sql.startsWith("SELECT") && bindings[0].equals(2L)) ||
                   (sql.startsWith("SELECT") && bindings[0].equals(3L))) {
            mock[0] = new MockResult(0, result);

        }
        return mock;
    }
}
