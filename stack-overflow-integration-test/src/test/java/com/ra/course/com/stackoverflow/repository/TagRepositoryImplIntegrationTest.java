package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.impl.TagRepositoryImpl;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
@ActiveProfiles("test")
public class TagRepositoryImplIntegrationTest {
    private long ID = 1L;
    private Tag tag = new Tag(ID, "Some Tag name", "Some Tag Description");

    @Autowired
    private TagRepositoryImpl tagRepository;
    @Autowired
    private DSLContext dslContext;

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
    public void whenSaveTagInDataBaseThenReturnTagWithId() {
        var savedTag = tagRepository.save(tag);

        assertThat(savedTag.getId() > 0).isTrue();
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
    public void whenDeleteTagFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //it's necessary only for deletion tag from db for this test!!!
        dslContext.execute("SET FOREIGN_KEY_CHECKS = 0;");
        tagRepository.delete(tag);

        Optional<Tag> result = tagRepository.findById(tag.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenUpdateTagInDatabaseThenGetUpdatedTag() {
        var before = tagRepository.findById(2L).get();
        before.setDescription("Test111");
        tagRepository.update(before);
        var after = tagRepository.findById(2L).get();

        assertThat(after.getDescription().equals("Test111")).isTrue();
    }
}
