package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.PhotoTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PhotoRecord extends UpdatableRecordImpl<PhotoRecord> implements Record5<Long, String, Timestamp, Long, Long> {

    private static final long serialVersionUID = -656736279;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setPhotoPath(String value) {
        set(1, value);
    }

    public String getPhotoPath() {
        return (String) get(1);
    }

    public void setCreationDate(Timestamp value) {
        set(2, value);
    }

    public Timestamp getCreationDate() {
        return (Timestamp) get(2);
    }

    public void setQuestionId(Long value) {
        set(3, value);
    }

    public Long getQuestionId() {
        return (Long) get(3);
    }

    public void setAnswerId(Long value) {
        set(4, value);
    }

    public Long getAnswerId() {
        return (Long) get(4);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row5<Long, String, Timestamp, Long, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, String, Timestamp, Long, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return PhotoTable.ID;
    }

    @Override
    public Field<String> field2() {
        return PhotoTable.PHOTO_PATH;
    }

    @Override
    public Field<Timestamp> field3() {
        return PhotoTable.CREATION_DATE;
    }

    @Override
    public Field<Long> field4() {
        return PhotoTable.QUESTION_ID;
    }

    @Override
    public Field<Long> field5() {
        return PhotoTable.ANSWER_ID;
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
    public Timestamp component3() {
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
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPhotoPath();
    }

    @Override
    public Timestamp value3() {
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
    public PhotoRecord value3(Timestamp value) {
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
    public PhotoRecord values(Long value1, String value2, Timestamp value3, Long value4, Long value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    public PhotoRecord() {
        super(PhotoTable.PHOTO_TABLE);
    }

    public PhotoRecord(Long id, String photoPath, Timestamp creationDate, Long questionId, Long answerId) {
        super(PhotoTable.PHOTO_TABLE);

        set(0, id);
        set(1, photoPath);
        set(2, creationDate);
        set(3, questionId);
        set(4, answerId);
    }
}
