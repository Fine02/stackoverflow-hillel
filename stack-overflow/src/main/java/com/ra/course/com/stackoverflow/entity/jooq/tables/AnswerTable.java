package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnswerTable extends TableImpl<AnswerRecord> {

    private static final long serialVersionUID = -124177582;
    public static final AnswerTable ANSWER_TABLE = new AnswerTable();

    @Override
    public Class<AnswerRecord> getRecordType() {
        return AnswerRecord.class;
    }

    public static final TableField<AnswerRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('answer_id_seq'::regclass)", BIGINT)), ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, String> ANSWER_TEXT = createField(DSL.name("answer_text"), CLOB.nullable(false), ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Boolean> ACCEPTED = createField(DSL.name("accepted"), BOOLEAN.nullable(false).defaultValue(DSL.field("false", BOOLEAN)), ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Integer> VOTE_COUNT = createField(DSL.name("vote_count"), INTEGER, ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Integer> FLAG_COUNT = createField(DSL.name("flag_count"), INTEGER, ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), TIMESTAMP.nullable(false), ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), BIGINT.nullable(false), ANSWER_TABLE, "");
    public static final TableField<AnswerRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT.nullable(false), ANSWER_TABLE, "");

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

    public AccountTable account() {
        return new AccountTable(this, Keys.ANSWER__FK_ANSWER_AUTHOR_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.ANSWER__FK_ANSWER_QUESTION_ID);
    }

    @Override
    public Row8<Long, String, Boolean, Integer, Integer, Timestamp, Long, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
