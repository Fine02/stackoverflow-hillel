package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedQuestionRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberVotedQuestionTable extends TableImpl<MemberVotedQuestionRecord> {

    private static final long serialVersionUID = 1489304967;
    public static final MemberVotedQuestionTable MEMBER_VOTED_QUESTION_TABLE = new MemberVotedQuestionTable();

    @Override
    public Class<MemberVotedQuestionRecord> getRecordType() {
        return MemberVotedQuestionRecord.class;
    }

    public static final TableField<MemberVotedQuestionRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), MEMBER_VOTED_QUESTION_TABLE, "");
    public static final TableField<MemberVotedQuestionRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT.nullable(false), MEMBER_VOTED_QUESTION_TABLE, "");
    public static final TableField<MemberVotedQuestionRecord, Boolean> UPVOTED = createField(DSL.name("upvoted"), BOOLEAN, MEMBER_VOTED_QUESTION_TABLE, "");

    private MemberVotedQuestionTable() {
        this(DSL.name("member_voted_question"), null);
    }

    private MemberVotedQuestionTable(Name alias, Table<MemberVotedQuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberVotedQuestionTable(Name alias, Table<MemberVotedQuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MemberVotedQuestionTable(Table<O> child, ForeignKey<O, MemberVotedQuestionRecord> key) {
        super(child, key, MEMBER_VOTED_QUESTION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_VOTED_QUESTION_PKEY);
    }

    @Override
    public UniqueKey<MemberVotedQuestionRecord> getPrimaryKey() {
        return Keys.MEMBER_VOTED_QUESTION_PKEY;
    }

    @Override
    public List<UniqueKey<MemberVotedQuestionRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberVotedQuestionRecord>>asList(Keys.MEMBER_VOTED_QUESTION_PKEY);
    }

    @Override
    public List<ForeignKey<MemberVotedQuestionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MemberVotedQuestionRecord, ?>>asList(Keys.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_ACCOUNT_ID, Keys.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_QUESTION_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_ACCOUNT_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_QUESTION_ID);
    }

    @Override
    public Row3<Long, Long, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
