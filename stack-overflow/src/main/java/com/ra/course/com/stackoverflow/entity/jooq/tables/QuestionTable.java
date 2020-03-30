/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq.tables;


import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.QuestionRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionTable extends TableImpl<QuestionRecord> {

    private static final long serialVersionUID = -437384880;

    /**
     * The reference instance of <code>public.question</code>
     */
    public static final QuestionTable QUESTION_TABLE = new QuestionTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuestionRecord> getRecordType() {
        return QuestionRecord.class;
    }

    /**
     * The column <code>public.question.id</code>.
     */
    public final TableField<QuestionRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('question_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.question.title</code>.
     */
    public final TableField<QuestionRecord, String> TITLE = createField(DSL.name("title"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.question.description</code>.
     */
    public final TableField<QuestionRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.question.view_count</code>.
     */
    public final TableField<QuestionRecord, Integer> VIEW_COUNT = createField(DSL.name("view_count"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.question.vote_count</code>.
     */
    public final TableField<QuestionRecord, Integer> VOTE_COUNT = createField(DSL.name("vote_count"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.question.creation_time</code>.
     */
    public final TableField<QuestionRecord, LocalDateTime> CREATION_TIME = createField(DSL.name("creation_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.question.update_time</code>.
     */
    public final TableField<QuestionRecord, LocalDateTime> UPDATE_TIME = createField(DSL.name("update_time"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.question.status_id</code>.
     */
    public final TableField<QuestionRecord, Long> STATUS_ID = createField(DSL.name("status_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.question.closing_remark_id</code>.
     */
    public final TableField<QuestionRecord, Long> CLOSING_REMARK_ID = createField(DSL.name("closing_remark_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.question.author_id</code>.
     */
    public final TableField<QuestionRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.question.bounty_id</code>.
     */
    public final TableField<QuestionRecord, Long> BOUNTY_ID = createField(DSL.name("bounty_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.question</code> table reference
     */
    public QuestionTable() {
        this(DSL.name("question"), null);
    }

    /**
     * Create an aliased <code>public.question</code> table reference
     */
    public QuestionTable(String alias) {
        this(DSL.name(alias), QUESTION_TABLE);
    }

    /**
     * Create an aliased <code>public.question</code> table reference
     */
    public QuestionTable(Name alias) {
        this(alias, QUESTION_TABLE);
    }

    private QuestionTable(Name alias, Table<QuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuestionTable(Name alias, Table<QuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> QuestionTable(Table<O> child, ForeignKey<O, QuestionRecord> key) {
        super(child, key, QUESTION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<QuestionRecord, Long> getIdentity() {
        return Keys.IDENTITY_QUESTION;
    }

    @Override
    public UniqueKey<QuestionRecord> getPrimaryKey() {
        return Keys.QUESTION_PKEY;
    }

    @Override
    public List<UniqueKey<QuestionRecord>> getKeys() {
        return Arrays.<UniqueKey<QuestionRecord>>asList(Keys.QUESTION_PKEY);
    }

    @Override
    public List<ForeignKey<QuestionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<QuestionRecord, ?>>asList(Keys.QUESTION__FK_STATUS_ID, Keys.QUESTION__FK_CLOSING_REMARK_ID, Keys.QUESTION__FK_AUTHOR_ID, Keys.QUESTION__FK_BOUNTY_ID);
    }

    public QuestionStatusTable questionStatus() {
        return new QuestionStatusTable(this, Keys.QUESTION__FK_STATUS_ID);
    }

    public QuestionClosingRemarkTable questionClosingRemark() {
        return new QuestionClosingRemarkTable(this, Keys.QUESTION__FK_CLOSING_REMARK_ID);
    }

    public MemberTable member() {
        return new MemberTable(this, Keys.QUESTION__FK_AUTHOR_ID);
    }

    public BountyTable bounty() {
        return new BountyTable(this, Keys.QUESTION__FK_BOUNTY_ID);
    }

    @Override
    public QuestionTable as(String alias) {
        return new QuestionTable(DSL.name(alias), this);
    }

    @Override
    public QuestionTable as(Name alias) {
        return new QuestionTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public QuestionTable rename(String name) {
        return new QuestionTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public QuestionTable rename(Name name) {
        return new QuestionTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, String, Integer, Integer, LocalDateTime, LocalDateTime, Long, Long, Long, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
