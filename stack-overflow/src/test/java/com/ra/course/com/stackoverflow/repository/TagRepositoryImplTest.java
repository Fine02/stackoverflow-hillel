package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.TagRecord;
import com.ra.course.com.stackoverflow.repository.impl.TagRepositoryImpl;
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

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagTable.TAG_TABLE;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryImplTest {
    private long ID = 1L;
    private Tag tag = new Tag(ID, "JAVA", "JAVA Teg Description");

    // Initialise data provider
    MockDataProvider provider = new MockProvider();
    MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise TagRepository with mocked DSL
    TagRepository tagRepository = new TagRepositoryImpl(dslContext);


    @Test
    public void whenFindTagByIdAndTagPresentInDataBaseThenReturnTag() {
        var tag = tagRepository.findById(2L).get();

        assertEquals(tag.getId(), 2L);
    }

    @Test
    public void whenFindTagByIdAndTagNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Tag> tag = tagRepository.findById(666L);

        assertThat(tag.isEmpty()).isTrue();
    }

    @Test
    public void whenTryFindByIdNullThenThrowException() {
        assertThatThrownBy(() -> tagRepository.findById(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenSaveTagInDataBaseThenReturnTagWithId() {
        var savedTag = tagRepository.save(tag);

        assertThat(savedTag.getId() > 0).isTrue();
    }

    @Test
    public void whenTrySaveNullThenThrowException() {
        assertThatThrownBy(() -> tagRepository.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenFindByTagNameThenReturnTag() {
        var result = tagRepository.findByTagName("SQL").get();

        assertEquals(result.getName(), "SQL");
    }

    @Test
    public void whenFindByTagNameButNotFoundThenReturnOptionalEmpty() {
        var result = tagRepository.findByTagName("Blah Blah");

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whetTryFindByTagNameWithNullArgumentThenThrowException() {
        assertThatThrownBy(() -> tagRepository.findByTagName(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenDeleteTagFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        var tagForDeleting = new Tag(666L, "JAVA", "JAVA Teg Description");
        tagRepository.delete(tagForDeleting);

        Optional<Tag> result = tagRepository.findById(tagForDeleting.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenTryDeleteNullThenThrowException() {
        assertThatThrownBy(() -> tagRepository.delete(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenUpdateTagInDatabaseThenGetUpdatedTag() {
        var before = new Tag(777L, "JAVA", "JAVA Teg Description");
        tagRepository.save(before);

        before.setDescription("Test111");
        tagRepository.update(before);
        var after = tagRepository.findById(777L).get();

        assertThat(after.getDescription().equals("Test111")).isTrue();
    }

    @Test
    public void whenTryUpdateNullThenThrowException() {
        assertThatThrownBy(() -> tagRepository.update(null))
                .isInstanceOf(NullPointerException.class);
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
            var result = create.newResult(TAG_TABLE.ID, TAG_TABLE.NAME, TAG_TABLE.DESCRIPTION);
            var tagRecordID1 = new TagRecord(1L, "JAVA", "Some tag description");
            var tagRecordID2 = new TagRecord(2L, "C#", "Some tag description");
            var tagRecordSQL = new TagRecord(3L, "SQL", "Some tag description");
            var tagRecordID777 = new TagRecord(777L, "JAVA", "Test111");



            if (sql.startsWith("INSERT") || (sql.startsWith("SELECT")&& value[0].equals(1L))) {
                result.add(tagRecordID1);

                mock[0] = new MockResult(1, result);
            }else if (sql.startsWith("SELECT") && value[0].equals(2L)) {
                result.add(tagRecordID2);

                mock[0] = new MockResult(1, result);
            }else if (sql.startsWith("SELECT") && value[0].equals(777L)) {
                result.add(tagRecordID777);

                mock[0] = new MockResult(1, result);
            }else if (sql.startsWith("SELECT") && value[0].equals("SQL")) {
                result.add(tagRecordSQL);

                mock[0] = new MockResult(1, result);
            }else if (sql.startsWith("SELECT") && (value[0].equals(666L) || value[0].equals("Blah Blah"))) {

                mock[0] = new MockResult(0, result);
            }else {

                mock[0] = new MockResult(1, result);
            }

            return mock;
        }
    }
}
