package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagQuestionTable.TAG_QUESTION_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagTable.TAG_TABLE;

@Repository
@AllArgsConstructor
public class TagRepositoryJooqImpl implements TagRepository {

    private final DSLContext dslContext;

    @Override
    public Tag save(final Tag tag) {
        return dslContext.insertInto(TAG_TABLE, TAG_TABLE.NAME, TAG_TABLE.DESCRIPTION)
                .values(tag.getName(), tag.getDescription())
                .returning()
                .fetchOne()
                .into(Tag.class);
    }

    @Override
    public Optional<Tag> findById( final Long id) {
        final var tagRecord = dslContext.fetchOne(TAG_TABLE, TAG_TABLE.ID.eq(id));

        return tagRecord != null ? Optional.of(tagRecord.into(Tag.class)) : Optional.empty();
    }

    @Override
    public void delete( final Tag tag) {
        dslContext.delete(TAG_TABLE)
                .where(TAG_TABLE.ID.eq(tag.getId()))
                .execute();
    }

    @Override
    public void update( final Tag tag) {
        dslContext.update(TAG_TABLE)
                .set(TAG_TABLE.DESCRIPTION, tag.getDescription())
                .where(TAG_TABLE.ID.eq(tag.getId()))
                .execute();
    }

    @Override
    public Optional<Tag> findByTagName( final String tagName) {
        final var tagRecord = dslContext.fetchOne(TAG_TABLE, TAG_TABLE.NAME.eq(tagName));

        return tagRecord != null ? Optional.of(tagRecord.into(Tag.class)) : Optional.empty();
    }

    @Override
    public List<Tag> findByQuestionId( final Long id) {
        return  dslContext.select(TAG_QUESTION_TABLE.TAG_ID)
                .from(TAG_QUESTION_TABLE)
                .where(TAG_QUESTION_TABLE.QUESTION_ID.eq(id))
                .fetch()
                .stream()
                .map(s -> s.getValue(TAG_QUESTION_TABLE.TAG_ID))
                .map(this::findById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tag> findAll() {
        return dslContext.selectFrom(TAG_TABLE)
                .fetchStreamInto(Tag.class)
                .collect(Collectors.toList());
    }
}