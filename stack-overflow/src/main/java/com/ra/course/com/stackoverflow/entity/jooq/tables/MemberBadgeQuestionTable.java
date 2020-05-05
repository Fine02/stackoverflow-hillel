package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.enums.BadgeType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberBadgeQuestionRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberBadgeQuestionTable extends TableImpl<MemberBadgeQuestionRecord> {

    private static final long serialVersionUID = 474483404;
    public static final MemberBadgeQuestionTable MEMBER_BADGE_QUESTION_TABLE = new MemberBadgeQuestionTable();

    @Override
    public Class<MemberBadgeQuestionRecord> getRecordType() {
        return MemberBadgeQuestionRecord.class;
    }

    public static final TableField<MemberBadgeQuestionRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), MEMBER_BADGE_QUESTION_TABLE, "");
    public static final TableField<MemberBadgeQuestionRecord, BadgeType> BADGE = createField(DSL.name("badge"), VARCHAR.nullable(false).asEnumDataType(BadgeType.class), MEMBER_BADGE_QUESTION_TABLE, "");
    public static final TableField<MemberBadgeQuestionRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT.nullable(false), MEMBER_BADGE_QUESTION_TABLE, "");

    private MemberBadgeQuestionTable() {
        this(DSL.name("member_badge_question"), null);
    }

    private MemberBadgeQuestionTable(Name alias, Table<MemberBadgeQuestionRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberBadgeQuestionTable(Name alias, Table<MemberBadgeQuestionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MemberBadgeQuestionTable(Table<O> child, ForeignKey<O, MemberBadgeQuestionRecord> key) {
        super(child, key, MEMBER_BADGE_QUESTION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_BADGE_QUESTION_PKEY);
    }

    @Override
    public UniqueKey<MemberBadgeQuestionRecord> getPrimaryKey() {
        return Keys.MEMBER_BADGE_QUESTION_PKEY;
    }

    @Override
    public List<UniqueKey<MemberBadgeQuestionRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberBadgeQuestionRecord>>asList(Keys.MEMBER_BADGE_QUESTION_PKEY);
    }

    @Override
    public List<ForeignKey<MemberBadgeQuestionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MemberBadgeQuestionRecord, ?>>asList(Keys.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_ACCOUNT_ID, Keys.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_QUESTION_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_ACCOUNT_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_QUESTION_ID);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, BadgeType, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
