package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedAnswerTable;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberVotedAnswerRecord extends UpdatableRecordImpl<MemberVotedAnswerRecord> implements Record3<Long, Long, Boolean> {

    private static final long serialVersionUID = 755802218;

    public void setAccountId(Long value) {
        set(0, value);
    }

    public Long getAccountId() {
        return (Long) get(0);
    }

    public void setAnswerId(Long value) {
        set(1, value);
    }

    public Long getAnswerId() {
        return (Long) get(1);
    }

    public void setUpvoted(Boolean value) {
        set(2, value);
    }

    public Boolean getUpvoted() {
        return (Boolean) get(2);
    }

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    @Override
    public Row3<Long, Long, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, Boolean> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MemberVotedAnswerTable.ACCOUNT_ID;
    }

    @Override
    public Field<Long> field2() {
        return MemberVotedAnswerTable.ANSWER_ID;
    }

    @Override
    public Field<Boolean> field3() {
        return MemberVotedAnswerTable.UPVOTED;
    }

    @Override
    public Long component1() {
        return getAccountId();
    }

    @Override
    public Long component2() {
        return getAnswerId();
    }

    @Override
    public Boolean component3() {
        return getUpvoted();
    }

    @Override
    public Long value1() {
        return getAccountId();
    }

    @Override
    public Long value2() {
        return getAnswerId();
    }

    @Override
    public Boolean value3() {
        return getUpvoted();
    }

    @Override
    public MemberVotedAnswerRecord value1(Long value) {
        setAccountId(value);
        return this;
    }

    @Override
    public MemberVotedAnswerRecord value2(Long value) {
        setAnswerId(value);
        return this;
    }

    @Override
    public MemberVotedAnswerRecord value3(Boolean value) {
        setUpvoted(value);
        return this;
    }

    @Override
    public MemberVotedAnswerRecord values(Long value1, Long value2, Boolean value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    public MemberVotedAnswerRecord() {
        super(MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE);
    }

    public MemberVotedAnswerRecord(Long accountId, Long answerId, Boolean upvoted) {
        super(MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE);

        set(0, accountId);
        set(1, answerId);
        set(2, upvoted);
    }
}
