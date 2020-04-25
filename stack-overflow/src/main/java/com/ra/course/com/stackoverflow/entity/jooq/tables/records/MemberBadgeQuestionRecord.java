package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.enums.BadgeType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.MemberBadgeQuestionTable;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberBadgeQuestionRecord extends UpdatableRecordImpl<MemberBadgeQuestionRecord> implements Record3<Long, BadgeType, Long> {

    private static final long serialVersionUID = -1523455686;

    public void setAccountId(Long value) {
        set(0, value);
    }

    public Long getAccountId() {
        return (Long) get(0);
    }

    public void setBadge(BadgeType value) {
        set(1, value);
    }

    public BadgeType getBadge() {
        return (BadgeType) get(1);
    }

    public void setQuestionId(Long value) {
        set(2, value);
    }

    public Long getQuestionId() {
        return (Long) get(2);
    }

    @Override
    public Record3<Long, BadgeType, Long> key() {
        return (Record3) super.key();
    }

    @Override
    public Row3<Long, BadgeType, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, BadgeType, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MemberBadgeQuestionTable.ACCOUNT_ID;
    }

    @Override
    public Field<BadgeType> field2() {
        return MemberBadgeQuestionTable.BADGE;
    }

    @Override
    public Field<Long> field3() {
        return MemberBadgeQuestionTable.QUESTION_ID;
    }

    @Override
    public Long component1() {
        return getAccountId();
    }

    @Override
    public BadgeType component2() {
        return getBadge();
    }

    @Override
    public Long component3() {
        return getQuestionId();
    }

    @Override
    public Long value1() {
        return getAccountId();
    }

    @Override
    public BadgeType value2() {
        return getBadge();
    }

    @Override
    public Long value3() {
        return getQuestionId();
    }

    @Override
    public MemberBadgeQuestionRecord value1(Long value) {
        setAccountId(value);
        return this;
    }

    @Override
    public MemberBadgeQuestionRecord value2(BadgeType value) {
        setBadge(value);
        return this;
    }

    @Override
    public MemberBadgeQuestionRecord value3(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public MemberBadgeQuestionRecord values(Long value1, BadgeType value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    public MemberBadgeQuestionRecord() {
        super(MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE);
    }

    public MemberBadgeQuestionRecord(Long accountId, BadgeType badge, Long questionId) {
        super(MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE);

        set(0, accountId);
        set(1, badge);
        set(2, questionId);
    }
}
