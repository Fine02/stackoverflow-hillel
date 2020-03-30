/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq.tables.records;


import com.ra.course.com.stackoverflow.entity.jooq.tables.PhotoTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PhotoRecord extends UpdatableRecordImpl<PhotoRecord> implements Record6<Long, String, LocalDateTime, Long, Long, Long> {

    private static final long serialVersionUID = 2090458106;

    /**
     * Setter for <code>public.photo.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.photo.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.photo.photo_path</code>.
     */
    public void setPhotoPath(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.photo.photo_path</code>.
     */
    public String getPhotoPath() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.photo.creation_date</code>.
     */
    public void setCreationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.photo.creation_date</code>.
     */
    public LocalDateTime getCreationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.photo.question_id</code>.
     */
    public void setQuestionId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.photo.question_id</code>.
     */
    public Long getQuestionId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>public.photo.answer_id</code>.
     */
    public void setAnswerId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.photo.answer_id</code>.
     */
    public Long getAnswerId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.photo.comment_id</code>.
     */
    public void setCommentId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.photo.comment_id</code>.
     */
    public Long getCommentId() {
        return (Long) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, LocalDateTime, Long, Long, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, LocalDateTime, Long, Long, Long> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return PhotoTable.PHOTO_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return PhotoTable.PHOTO_TABLE.PHOTO_PATH;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return PhotoTable.PHOTO_TABLE.CREATION_DATE;
    }

    @Override
    public Field<Long> field4() {
        return PhotoTable.PHOTO_TABLE.QUESTION_ID;
    }

    @Override
    public Field<Long> field5() {
        return PhotoTable.PHOTO_TABLE.ANSWER_ID;
    }

    @Override
    public Field<Long> field6() {
        return PhotoTable.PHOTO_TABLE.COMMENT_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPhotoPath();
    }

    @Override
    public LocalDateTime component3() {
        return getCreationDate();
    }

    @Override
    public Long component4() {
        return getQuestionId();
    }

    @Override
    public Long component5() {
        return getAnswerId();
    }

    @Override
    public Long component6() {
        return getCommentId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPhotoPath();
    }

    @Override
    public LocalDateTime value3() {
        return getCreationDate();
    }

    @Override
    public Long value4() {
        return getQuestionId();
    }

    @Override
    public Long value5() {
        return getAnswerId();
    }

    @Override
    public Long value6() {
        return getCommentId();
    }

    @Override
    public PhotoRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public PhotoRecord value2(String value) {
        setPhotoPath(value);
        return this;
    }

    @Override
    public PhotoRecord value3(LocalDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public PhotoRecord value4(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public PhotoRecord value5(Long value) {
        setAnswerId(value);
        return this;
    }

    @Override
    public PhotoRecord value6(Long value) {
        setCommentId(value);
        return this;
    }

    @Override
    public PhotoRecord values(Long value1, String value2, LocalDateTime value3, Long value4, Long value5, Long value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PhotoRecord
     */
    public PhotoRecord() {
        super(PhotoTable.PHOTO_TABLE);
    }

    /**
     * Create a detached, initialised PhotoRecord
     */
    public PhotoRecord(Long id, String photoPath, LocalDateTime creationDate, Long questionId, Long answerId, Long commentId) {
        super(PhotoTable.PHOTO_TABLE);

        set(0, id);
        set(1, photoPath);
        set(2, creationDate);
        set(3, questionId);
        set(4, answerId);
        set(5, commentId);
    }
}
