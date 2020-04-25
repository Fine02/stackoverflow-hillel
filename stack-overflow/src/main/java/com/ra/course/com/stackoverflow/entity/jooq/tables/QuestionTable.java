package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.QuestionRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionTable extends TableImpl<QuestionRecord> {

    private static final long serialVersionUID = -63360502;
    public static final QuestionTable QUESTION_TABLE = new QuestionTable();

    @Override
    public Class<QuestionRecord> getRecordType() {
        return QuestionRecord.class;
    }

    public static final TableField<QuestionRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('question_id_seq'::regclass)", BIGINT)), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, String> TITLE = createField(DSL.name("title"), VARCHAR(255).nullable(false), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, String> DESCRIPTION = createField(DSL.name("description"), CLOB.nullable(false), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Integer> VIEW_COUNT = createField(DSL.name("view_count"), INTEGER, QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Integer> VOTE_COUNT = createField(DSL.name("vote_count"), INTEGER, QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Timestamp> CREATION_TIME = createField(DSL.name("creation_time"), TIMESTAMP.nullable(false), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Timestamp> UPDATE_TIME = createField(DSL.name("update_time"), TIMESTAMP.nullable(false), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, QuestionStatusType> STATUS = createField(DSL.name("status"), VARCHAR.asEnumDataType(QuestionStatusType.class), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, QuestionClosingRemarkType> CLOSING_REMARK = createField(DSL.name("closing_remark"), VARCHAR.asEnumDataType(QuestionClosingRemarkType.class), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), BIGINT.nullable(false), QUESTION_TABLE, "");
    public static final TableField<QuestionRecord, Long> BOUNTY_ID = createField(DSL.name("bounty_id"), BIGINT, QUESTION_TABLE, "");

    private QuestionTable() {
        this(DSL.name("question"), null);
    }

    private QuestionTable(Name alias, Table<QuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuestionTable(Name alias, Table<QuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> QuestionTable(Table<O> child, ForeignKey<O, QuestionRecord> key) {
        super(child, key, QUESTION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUESTION_PKEY);
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
        return Arrays.<ForeignKey<QuestionRecord, ?>>asList(Keys.QUESTION__FK_AUTHOR_ID, Keys.QUESTION__FK_BOUNTY_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.QUESTION__FK_AUTHOR_ID);
    }

    public BountyTable bounty() {
        return new BountyTable(this, Keys.QUESTION__FK_BOUNTY_ID);
    }

    @Override
    public Row11<Long, String, String, Integer, Integer, Timestamp, Timestamp, QuestionStatusType, QuestionClosingRemarkType, Long, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
