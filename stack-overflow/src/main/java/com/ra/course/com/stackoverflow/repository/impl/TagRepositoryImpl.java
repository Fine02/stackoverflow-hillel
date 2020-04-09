package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagTable.TAG_TABLE;

@Repository
@AllArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final DSLContext dslContext;

    @Override
    public Tag save(@NonNull final Tag tag) {
        return dslContext.insertInto(TAG_TABLE, TAG_TABLE.NAME, TAG_TABLE.DESCRIPTION)
                .values(tag.getName(), tag.getDescription())
                .returning()
                .fetchOne()
                .into(Tag.class);
    }

    @Override
    public Optional<Tag> findById(final long id) {
        final var tagRecord = dslContext.fetchOne(TAG_TABLE, TAG_TABLE.ID.eq(id));

        return tagRecord != null ? Optional.of(tagRecord.into(Tag.class)) : Optional.empty();
    }

    @Override
    public void delete(@NonNull final Tag tag) {
        dslContext.delete(TAG_TABLE)
                .where(TAG_TABLE.ID.eq(tag.getId()))
                .execute();
    }

    @Override
    public void update(@NonNull final Tag tag) {
        dslContext.update(TAG_TABLE)
                .set(TAG_TABLE.NAME, tag.getName())
                .set(TAG_TABLE.DESCRIPTION, tag.getDescription())
                .where(TAG_TABLE.ID.eq(tag.getId()))
                .execute();
    }

    @Override
    public Optional<Tag> findByTagName(@NonNull final String tagName) {
        final var tagRecord = dslContext.fetchOne(TAG_TABLE, TAG_TABLE.NAME.eq(tagName));

        return tagRecord != null ? Optional.of(tagRecord.into(Tag.class)) : Optional.empty();
    }

}