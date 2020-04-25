package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommentTable extends TableImpl<CommentRecord> {

    private static final long serialVersionUID = 1056413735;
    public static final CommentTable COMMENT_TABLE = new CommentTable();

    @Override
    public Class<CommentRecord> getRecordType() {
        return CommentRecord.class;
    }

    public static final TableField<CommentRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('comment_id_seq'::regclass)", BIGINT)), COMMENT_TABLE, "");
    public static final TableField<CommentRecord, String> COMMENT_TEXT = createField(DSL.name("comment_text"), VARCHAR(255).nullable(false), COMMENT_TABLE, "");
    public static final TableField<CommentRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), TIMESTAMP.nullable(false), COMMENT_TABLE, "");
    public static final TableField<CommentRecord, Integer> VOTE_COUNT = createField(DSL.name("vote_count"), INTEGER, COMMENT_TABLE, "");
    public static final TableField<CommentRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), BIGINT.nullable(false), COMMENT_TABLE, "");
    public static final TableField<CommentRecord, Long> ANSWER_ID = createField(DSL.name("answer_id"), BIGINT, COMMENT_TABLE, "");
    public static final TableField<CommentRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT, COMMENT_TABLE, "");

    private CommentTable() {
        this(DSL.name("comment"), null);
    }

    private CommentTable(Name alias, Table<CommentRecord> aliased) {
        this(alias, aliased, null);
    }

    private CommentTable(Name alias, Table<CommentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> CommentTable(Table<O> child, ForeignKey<O, CommentRecord> key) {
        super(child, key, COMMENT_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.COMMENT_PKEY);
    }

    @Override
    public Identity<CommentRecord, Long> getIdentity() {
        return Keys.IDENTITY_COMMENT;
    }

    @Override
    public UniqueKey<CommentRecord> getPrimaryKey() {
        return Keys.COMMENT_PKEY;
    }

    @Override
    public List<UniqueKey<CommentRecord>> getKeys() {
        return Arrays.<UniqueKey<CommentRecord>>asList(Keys.COMMENT_PKEY);
    }

    @Override
    public List<ForeignKey<CommentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CommentRecord, ?>>asList(Keys.COMMENT__FK_COMMENT_AUTHOR_ID, Keys.COMMENT__FK_COMMENT_ANSWER_ID, Keys.COMMENT__FK_COMMENT_QUESTION_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.COMMENT__FK_COMMENT_AUTHOR_ID);
    }

    public AnswerTable answer() {
        return new AnswerTable(this, Keys.COMMENT__FK_COMMENT_ANSWER_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.COMMENT__FK_COMMENT_QUESTION_ID);
    }

    @Override
    public Row7<Long, String, Timestamp, Integer, Long, Long, Long> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
