/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq.tables;


import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnswerTable extends TableImpl<AnswerRecord> {

    private static final long serialVersionUID = 483536241;

    /**
     * The reference instance of <code>public.answer</code>
     */
    public static final AnswerTable ANSWER_TABLE = new AnswerTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnswerRecord> getRecordType() {
        return AnswerRecord.class;
    }

    /**
     * The column <code>public.answer.id</code>.
     */
    public static final TableField<AnswerRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('answer_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.answer_text</code>.
     */
    public static final TableField<AnswerRecord, String> ANSWER_TEXT = createField(DSL.name("answer_text"), org.jooq.impl.SQLDataType.CLOB.nullable(false), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.accepted</code>.
     */
    public static final TableField<AnswerRecord, Boolean> ACCEPTED = createField(DSL.name("accepted"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.vote_count</code>.
     */
    public static final TableField<AnswerRecord, Integer> VOTE_COUNT = createField(DSL.name("vote_count"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.flag_count</code>.
     */
    public static final TableField<AnswerRecord, Integer> FLAG_COUNT = createField(DSL.name("flag_count"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.creation_date</code>.
     */
    public static final TableField<AnswerRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.author_id</code>.
     */
    public static final TableField<AnswerRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), ANSWER_TABLE, "");

    /**
     * The column <code>public.answer.question_id</code>.
     */
    public static final TableField<AnswerRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), ANSWER_TABLE, "");

    /**
     * No further instances allowed
     */
    private AnswerTable() {
        this(DSL.name("answer"), null);
    }

    private AnswerTable(Name alias, Table<AnswerRecord> aliased) {
        this(alias, aliased, null);
    }

    private AnswerTable(Name alias, Table<AnswerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AnswerTable(Table<O> child, ForeignKey<O, AnswerRecord> key) {
        super(child, key, ANSWER_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ANSWER_PKEY);
    }

    @Override
    public Identity<AnswerRecord, Long> getIdentity() {
        return Keys.IDENTITY_ANSWER;
    }

    @Override
    public UniqueKey<AnswerRecord> getPrimaryKey() {
        return Keys.ANSWER_PKEY;
    }

    @Override
    public List<UniqueKey<AnswerRecord>> getKeys() {
        return Arrays.<UniqueKey<AnswerRecord>>asList(Keys.ANSWER_PKEY);
    }

    @Override
    public List<ForeignKey<AnswerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AnswerRecord, ?>>asList(Keys.ANSWER__FK_ANSWER_AUTHOR_ID, Keys.ANSWER__FK_ANSWER_QUESTION_ID);
    }

    public MemberTable member() {
        return new MemberTable(this, Keys.ANSWER__FK_ANSWER_AUTHOR_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.ANSWER__FK_ANSWER_QUESTION_ID);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, Boolean, Integer, Integer, Timestamp, Long, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
