package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedCommentTable;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberVotedCommentRecord extends UpdatableRecordImpl<MemberVotedCommentRecord> implements Record3<Long, Long, Boolean> {

    private static final long serialVersionUID = 1642250688;

    public void setAccountId(Long value) {
        set(0, value);
    }

    public Long getAccountId() {
        return (Long) get(0);
    }

    public void setCommentId(Long value) {
        set(1, value);
    }

    public Long getCommentId() {
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
        return MemberVotedCommentTable.ACCOUNT_ID;
    }

    @Override
    public Field<Long> field2() {
        return MemberVotedCommentTable.COMMENT_ID;
    }

    @Override
    public Field<Boolean> field3() {
        return MemberVotedCommentTable.UPVOTED;
    }

    @Override
    public Long component1() {
        return getAccountId();
    }

    @Override
    public Long component2() {
        return getCommentId();
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
        return getCommentId();
    }

    @Override
    public Boolean value3() {
        return getUpvoted();
    }

    @Override
    public MemberVotedCommentRecord value1(Long value) {
        setAccountId(value);
        return this;
    }

    @Override
    public MemberVotedCommentRecord value2(Long value) {
        setCommentId(value);
        return this;
    }

    @Override
    public MemberVotedCommentRecord value3(Boolean value) {
        setUpvoted(value);
        return this;
    }

    @Override
    public MemberVotedCommentRecord values(Long value1, Long value2, Boolean value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    public MemberVotedCommentRecord() {
        super(MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE);
    }

    public MemberVotedCommentRecord(Long accountId, Long commentId, Boolean upvoted) {
        super(MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE);

        set(0, accountId);
        set(1, commentId);
        set(2, upvoted);
    }
}
