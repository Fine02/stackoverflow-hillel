package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedAnswerRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberVotedAnswerTable extends TableImpl<MemberVotedAnswerRecord> {

    private static final long serialVersionUID = 729320863;
    public static final MemberVotedAnswerTable MEMBER_VOTED_ANSWER_TABLE = new MemberVotedAnswerTable();

    @Override
    public Class<MemberVotedAnswerRecord> getRecordType() {
        return MemberVotedAnswerRecord.class;
    }

    public static final TableField<MemberVotedAnswerRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), MEMBER_VOTED_ANSWER_TABLE, "");
    public static final TableField<MemberVotedAnswerRecord, Long> ANSWER_ID = createField(DSL.name("answer_id"), BIGINT.nullable(false), MEMBER_VOTED_ANSWER_TABLE, "");
    public static final TableField<MemberVotedAnswerRecord, Boolean> UPVOTED = createField(DSL.name("upvoted"), BOOLEAN, MEMBER_VOTED_ANSWER_TABLE, "");

    private MemberVotedAnswerTable() {
        this(DSL.name("member_voted_answer"), null);
    }

    private MemberVotedAnswerTable(Name alias, Table<MemberVotedAnswerRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberVotedAnswerTable(Name alias, Table<MemberVotedAnswerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MemberVotedAnswerTable(Table<O> child, ForeignKey<O, MemberVotedAnswerRecord> key) {
        super(child, key, MEMBER_VOTED_ANSWER_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_VOTED_ANSWER_PKEY);
    }

    @Override
    public UniqueKey<MemberVotedAnswerRecord> getPrimaryKey() {
        return Keys.MEMBER_VOTED_ANSWER_PKEY;
    }

    @Override
    public List<UniqueKey<MemberVotedAnswerRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberVotedAnswerRecord>>asList(Keys.MEMBER_VOTED_ANSWER_PKEY);
    }

    @Override
    public List<ForeignKey<MemberVotedAnswerRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MemberVotedAnswerRecord, ?>>asList(Keys.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ACCOUNT_ID, Keys.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ANSWER_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ACCOUNT_ID);
    }

    public AnswerTable answer() {
        return new AnswerTable(this, Keys.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ANSWER_ID);
    }

    @Override
    public Row3<Long, Long, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
