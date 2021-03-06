package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.QuestionMemberQuestionClosingRemarkRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionMemberQuestionClosingRemarkTable extends TableImpl<QuestionMemberQuestionClosingRemarkRecord> {

    private static final long serialVersionUID = 17528596;
    public static final QuestionMemberQuestionClosingRemarkTable QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE = new QuestionMemberQuestionClosingRemarkTable();

    @Override
    public Class<QuestionMemberQuestionClosingRemarkRecord> getRecordType() {
        return QuestionMemberQuestionClosingRemarkRecord.class;
    }

    public static final TableField<QuestionMemberQuestionClosingRemarkRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT.nullable(false), QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "");
    public static final TableField<QuestionMemberQuestionClosingRemarkRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "");
    public static final TableField<QuestionMemberQuestionClosingRemarkRecord, QuestionClosingRemarkType> CLOSING_REMARK = createField(DSL.name("closing_remark"), VARCHAR.nullable(false).asEnumDataType(QuestionClosingRemarkType.class), QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "");
    public static final TableField<QuestionMemberQuestionClosingRemarkRecord, Boolean> MARKED_FOR_CLOSING = createField(DSL.name("marked_for_closing"), BOOLEAN.nullable(false), QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "");
    public static final TableField<QuestionMemberQuestionClosingRemarkRecord, Boolean> MARKED_FOR_DELETING = createField(DSL.name("marked_for_deleting"), BOOLEAN.nullable(false), QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "");

    private QuestionMemberQuestionClosingRemarkTable() {
        this(DSL.name("question_member_question_closing_remark"), null);
    }

    private QuestionMemberQuestionClosingRemarkTable(Name alias, Table<QuestionMemberQuestionClosingRemarkRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuestionMemberQuestionClosingRemarkTable(Name alias, Table<QuestionMemberQuestionClosingRemarkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> QuestionMemberQuestionClosingRemarkTable(Table<O> child, ForeignKey<O, QuestionMemberQuestionClosingRemarkRecord> key) {
        super(child, key, QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY);
    }

    @Override
    public UniqueKey<QuestionMemberQuestionClosingRemarkRecord> getPrimaryKey() {
        return Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY;
    }

    @Override
    public List<UniqueKey<QuestionMemberQuestionClosingRemarkRecord>> getKeys() {
        return Arrays.<UniqueKey<QuestionMemberQuestionClosingRemarkRecord>>asList(Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY);
    }

    @Override
    public List<ForeignKey<QuestionMemberQuestionClosingRemarkRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<QuestionMemberQuestionClosingRemarkRecord, ?>>asList(Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_QUESTION_ID, Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_ACCOUNT_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_QUESTION_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_ACCOUNT_ID);
    }

    @Override
    public Row5<Long, Long, QuestionClosingRemarkType, Boolean, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
