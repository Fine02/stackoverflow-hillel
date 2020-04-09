package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Photo;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.PhotoRecord;
import com.ra.course.com.stackoverflow.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.PHOTO_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.PhotoTable.*;

@Repository
@AllArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

    private transient final DSLContext dslContext;

    @Override
    public Photo save(final Photo photo) {
        final PhotoRecord photoRecord = dslContext.insertInto(PHOTO_TABLE, PHOTO_PATH, CREATION_DATE, QUESTION_ID, ANSWER_ID)
                                                  .values(photo.getPhotoPath(), Timestamp.valueOf(photo.getCreationDate()), photo.getQuestionId(), photo.getAnswerId())
                                                  .returning()
                                                  .fetchOne();

        return new Photo(photoRecord.getId(), photoRecord.getPhotoPath(), photoRecord.getCreationDate().toLocalDateTime(), photoRecord.getQuestionId(), photoRecord.getAnswerId());
    }

    @Override
    public Optional<Photo> findById(final Long id) {
        final Photo photo = dslContext.selectFrom(PHOTO_TABLE)
                                      .where(PHOTO_TABLE.ID.eq(id))
                                      .fetchAnyInto(Photo.class);
        return Optional.ofNullable(photo);
    }

    @Override
    public void deleteById(final Long id) {
        dslContext.delete(PHOTO_TABLE).where(PHOTO_TABLE.ID.eq(id)).execute();

    }

    @Override
    public List<Photo> findByAnswerId(final Long answerId) {
         return dslContext.selectFrom(PHOTO_TABLE)
                         .where(ANSWER_ID.eq(answerId))
                         .fetchStreamInto(Photo.class)
                         .collect(Collectors.toList());
    }

    @Override
    public List<Photo> findByQuestionId(final Long id) {
        return dslContext.selectFrom(PHOTO_TABLE)
                         .where(QUESTION_ID.eq(id))
                         .fetchStreamInto(Photo.class)
                         .collect(Collectors.toList());
    }
}