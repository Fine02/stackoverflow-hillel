package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.TagQuestionTable;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagQuestionRecord extends TableRecordImpl<TagQuestionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = -1696272046;

    public void setTagId(Long value) {
        set(0, value);
    }

    public Long getTagId() {
        return (Long) get(0);
    }

    public void setQuestionId(Long value) {
        set(1, value);
    }

    public Long getQuestionId() {
        return (Long) get(1);
    }

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return TagQuestionTable.TAG_ID;
    }

    @Override
    public Field<Long> field2() {
        return TagQuestionTable.QUESTION_ID;
    }

    @Override
    public Long component1() {
        return getTagId();
    }

    @Override
    public Long component2() {
        return getQuestionId();
    }

    @Override
    public Long value1() {
        return getTagId();
    }

    @Override
    public Long value2() {
        return getQuestionId();
    }

    @Override
    public TagQuestionRecord value1(Long value) {
        setTagId(value);
        return this;
    }

    @Override
    public TagQuestionRecord value2(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public TagQuestionRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    public TagQuestionRecord() {
        super(TagQuestionTable.TAG_QUESTION_TABLE);
    }

    public TagQuestionRecord(Long tagId, Long questionId) {
        super(TagQuestionTable.TAG_QUESTION_TABLE);

        set(0, tagId);
        set(1, questionId);
    }
}
