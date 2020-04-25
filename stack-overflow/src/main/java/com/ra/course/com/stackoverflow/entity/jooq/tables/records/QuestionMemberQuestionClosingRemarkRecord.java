package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.QuestionMemberQuestionClosingRemarkTable;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionMemberQuestionClosingRemarkRecord extends UpdatableRecordImpl<QuestionMemberQuestionClosingRemarkRecord> implements Record5<Long, Long, QuestionClosingRemarkType, Boolean, Boolean> {

    private static final long serialVersionUID = 1051134226;

    public void setQuestionId(Long value) {
        set(0, value);
    }

    public Long getQuestionId() {
        return (Long) get(0);
    }

    public void setAccountId(Long value) {
        set(1, value);
    }

    public Long getAccountId() {
        return (Long) get(1);
    }

    public void setClosingRemark(QuestionClosingRemarkType value) {
        set(2, value);
    }

    public QuestionClosingRemarkType getClosingRemark() {
        return (QuestionClosingRemarkType) get(2);
    }

    public void setMarkedForClosing(Boolean value) {
        set(3, value);
    }

    public Boolean getMarkedForClosing() {
        return (Boolean) get(3);
    }

    public void setMarkedForDeleting(Boolean value) {
        set(4, value);
    }

    public Boolean getMarkedForDeleting() {
        return (Boolean) get(4);
    }

    @Override
    public Record3<Long, Long, QuestionClosingRemarkType> key() {
        return (Record3) super.key();
    }

    @Override
    public Row5<Long, Long, QuestionClosingRemarkType, Boolean, Boolean> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Long, QuestionClosingRemarkType, Boolean, Boolean> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuestionMemberQuestionClosingRemarkTable.QUESTION_ID;
    }

    @Override
    public Field<Long> field2() {
        return QuestionMemberQuestionClosingRemarkTable.ACCOUNT_ID;
    }

    @Override
    public Field<QuestionClosingRemarkType> field3() {
        return QuestionMemberQuestionClosingRemarkTable.CLOSING_REMARK;
    }

    @Override
    public Field<Boolean> field4() {
        return QuestionMemberQuestionClosingRemarkTable.MARKED_FOR_CLOSING;
    }

    @Override
    public Field<Boolean> field5() {
        return QuestionMemberQuestionClosingRemarkTable.MARKED_FOR_DELETING;
    }

    @Override
    public Long component1() {
        return getQuestionId();
    }

    @Override
    public Long component2() {
        return getAccountId();
    }

    @Override
    public QuestionClosingRemarkType component3() {
        return getClosingRemark();
    }

    @Override
    public Boolean component4() {
        return getMarkedForClosing();
    }

    @Override
    public Boolean component5() {
        return getMarkedForDeleting();
    }

    @Override
    public Long value1() {
        return getQuestionId();
    }

    @Override
    public Long value2() {
        return getAccountId();
    }

    @Override
    public QuestionClosingRemarkType value3() {
        return getClosingRemark();
    }

    @Override
    public Boolean value4() {
        return getMarkedForClosing();
    }

    @Override
    public Boolean value5() {
        return getMarkedForDeleting();
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord value1(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord value2(Long value) {
        setAccountId(value);
        return this;
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord value3(QuestionClosingRemarkType value) {
        setClosingRemark(value);
        return this;
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord value4(Boolean value) {
        setMarkedForClosing(value);
        return this;
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord value5(Boolean value) {
        setMarkedForDeleting(value);
        return this;
    }

    @Override
    public QuestionMemberQuestionClosingRemarkRecord values(Long value1, Long value2, QuestionClosingRemarkType value3, Boolean value4, Boolean value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    public QuestionMemberQuestionClosingRemarkRecord() {
        super(QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE);
    }

    public QuestionMemberQuestionClosingRemarkRecord(Long questionId, Long accountId, QuestionClosingRemarkType closingRemark, Boolean markedForClosing, Boolean markedForDeleting) {
        super(QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE);

        set(0, questionId);
        set(1, accountId);
        set(2, closingRemark);
        set(3, markedForClosing);
        set(4, markedForDeleting);
    }
}
