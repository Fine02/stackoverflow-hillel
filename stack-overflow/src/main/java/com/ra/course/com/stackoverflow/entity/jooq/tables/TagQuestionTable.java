package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.TagQuestionRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.BIGINT;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagQuestionTable extends TableImpl<TagQuestionRecord> {

    private static final long serialVersionUID = -1865413301;
    public static final TagQuestionTable TAG_QUESTION_TABLE = new TagQuestionTable();

    @Override
    public Class<TagQuestionRecord> getRecordType() {
        return TagQuestionRecord.class;
    }

    public static final TableField<TagQuestionRecord, Long> TAG_ID = createField(DSL.name("tag_id"), BIGINT.nullable(false), TAG_QUESTION_TABLE, "");
    public static final TableField<TagQuestionRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT.nullable(false), TAG_QUESTION_TABLE, "");

    private TagQuestionTable() {
        this(DSL.name("tag_question"), null);
    }

    private TagQuestionTable(Name alias, Table<TagQuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private TagQuestionTable(Name alias, Table<TagQuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> TagQuestionTable(Table<O> child, ForeignKey<O, TagQuestionRecord> key) {
        super(child, key, TAG_QUESTION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<ForeignKey<TagQuestionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TagQuestionRecord, ?>>asList(Keys.TAG_QUESTION__FK_TAG_QUESTION_TAG_ID, Keys.TAG_QUESTION__FK_TAG_QUESTION_QUESTION_ID);
    }

    public TagTable tag() {
        return new TagTable(this, Keys.TAG_QUESTION__FK_TAG_QUESTION_TAG_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.TAG_QUESTION__FK_TAG_QUESTION_QUESTION_ID);
    }

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
