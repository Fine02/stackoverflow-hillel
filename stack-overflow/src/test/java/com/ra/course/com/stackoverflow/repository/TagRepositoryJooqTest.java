package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.TagRecord;
import com.ra.course.com.stackoverflow.repository.impl.TagRepositoryJooqImpl;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagTable.TAG_TABLE;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class TagRepositoryJooqTest {

    private static TagRepository data;

    private Tag tag;


    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new TagMockProvider()), SQLDialect.H2);

        // Initialise TagRepositoryJooqImpl with mocked DSL
        data = new TagRepositoryJooqImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        tag = getTag();
    }

    @Test
    public void whenSaveTagInDataBaseThenReturnTagWithId() {
        //given
        tag.setId(null);
        //when
        var result = data.save(tag);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenFindTagByIdAndTagPresentInDataBaseThenReturnTag() {
        //when
        var result =  data.findById(ID);
        //then
        assertEquals(Optional.of(tag), result);
    }

    @Test
    public void whenFindTagByIdAndTagNotPresentInDataBaseThenReturnOptionalEmpty() {
        //when
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenFindByTagNameThenReturnTag() {
        //when
        var result =  data.findByTagName(TITLE);
        //then
        assertEquals(Optional.of(tag), result);
    }

    @Test
    public void whenFindByTagNameButNotFoundThenReturnOptionalEmpty() {
        //when
        var result = data.findByTagName("Blah Blah");
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenDeleteTagFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //given
        tag.setId(666L);
        //when
        data.delete(tag);
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenUpdateTagInDatabaseThenGetUpdatedTag() {
        //given
        tag.setId(777L);
        tag.setDescription("Test111");
        //when
        data.update(tag);
        var result = data.findById(777L);
        //then
        assertEquals(Optional.of(tag), result);
    }

    @Test
    public void whenFindByByQuestionIdThenReturnListWithTag() {
        //when
        var listResult = data.findByQuestionId(ID);
        //then
        assertTrue(listResult.contains(tag));
    }

    @Test
    public void whenGetAllThenReturnListWithTag() {
        //when
        var listResult = data.findAll();
        //then
        assertTrue(listResult.contains(tag));
    }
}
    //implementation MockProvider for this test
class TagMockProvider implements MockDataProvider {

        @Override
        public MockResult[] execute(MockExecuteContext ctx) {

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            var dslContext = DSL.using(SQLDialect.H2);
            var mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            var sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            var result = dslContext.newResult(TAG_TABLE);

            var tagRecordID1 = new TagRecord(ID, TITLE, TEXT);
            var tagRecordID777 = new TagRecord(777L, TITLE, "Test111");

            //Stipulations for returning different results
            if (sql.startsWith("INSERT")  ||
                    (sql.startsWith("SELECT") && value.length == 0) ||
                    (sql.startsWith("SELECT")&& value[0].equals(ID)) ||
                    (sql.startsWith("SELECT") && value[0].equals(TITLE))) {
                result.add(tagRecordID1);
                mock[0] = new MockResult(1, result);
            } else if (sql.startsWith("SELECT") && value[0].equals(777L)) {
                result.add(tagRecordID777);
                mock[0] = new MockResult(1, result);
            } else {
                mock[0] = new MockResult(0, result);
            }

            return mock;
        }
    }

