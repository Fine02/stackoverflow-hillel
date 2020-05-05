package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.QuestionTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionRecord extends UpdatableRecordImpl<QuestionRecord> implements Record11<Long, String, String, Integer, Integer, Timestamp, Timestamp, QuestionStatusType, QuestionClosingRemarkType, Long, Long> {

    private static final long serialVersionUID = 1685995152;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setTitle(String value) {
        set(1, value);
    }

    public String getTitle() {
        return (String) get(1);
    }

    public void setDescription(String value) {
        set(2, value);
    }

    public String getDescription() {
        return (String) get(2);
    }

    public void setViewCount(Integer value) {
        set(3, value);
    }

    public Integer getViewCount() {
        return (Integer) get(3);
    }

    public void setVoteCount(Integer value) {
        set(4, value);
    }

    public Integer getVoteCount() {
        return (Integer) get(4);
    }

    public void setCreationTime(Timestamp value) {
        set(5, value);
    }

    public Timestamp getCreationTime() {
        return (Timestamp) get(5);
    }

    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
    }

    public void setStatus(QuestionStatusType value) {
        set(7, value);
    }

    public QuestionStatusType getStatus() {
        return (QuestionStatusType) get(7);
    }

    public void setClosingRemark(QuestionClosingRemarkType value) {
        set(8, value);
    }

    public QuestionClosingRemarkType getClosingRemark() {
        return (QuestionClosingRemarkType) get(8);
    }

    public void setAuthorId(Long value) {
        set(9, value);
    }

    public Long getAuthorId() {
        return (Long) get(9);
    }

    public void setBountyId(Long value) {
        set(10, value);
    }

    public Long getBountyId() {
        return (Long) get(10);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row11<Long, String, String, Integer, Integer, Timestamp, Timestamp, QuestionStatusType, QuestionClosingRemarkType, Long, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<Long, String, String, Integer, Integer, Timestamp, Timestamp, QuestionStatusType, QuestionClosingRemarkType, Long, Long> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuestionTable.ID;
    }

    @Override
    public Field<String> field2() {
        return QuestionTable.TITLE;
    }

    @Override
    public Field<String> field3() {
        return QuestionTable.DESCRIPTION;
    }

    @Override
    public Field<Integer> field4() {
        return QuestionTable.VIEW_COUNT;
    }

    @Override
    public Field<Integer> field5() {
        return QuestionTable.VOTE_COUNT;
    }

    @Override
    public Field<Timestamp> field6() {
        return QuestionTable.CREATION_TIME;
    }

    @Override
    public Field<Timestamp> field7() {
        return QuestionTable.UPDATE_TIME;
    }

    @Override
    public Field<QuestionStatusType> field8() {
        return QuestionTable.STATUS;
    }

    @Override
    public Field<QuestionClosingRemarkType> field9() {
        return QuestionTable.CLOSING_REMARK;
    }

    @Override
    public Field<Long> field10() {
        return QuestionTable.AUTHOR_ID;
    }

    @Override
    public Field<Long> field11() {
        return QuestionTable.BOUNTY_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getTitle();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public Integer component4() {
        return getViewCount();
    }

    @Override
    public Integer component5() {
        return getVoteCount();
    }

    @Override
    public Timestamp component6() {
        return getCreationTime();
    }

    @Override
    public Timestamp component7() {
        return getUpdateTime();
    }

    @Override
    public QuestionStatusType component8() {
        return getStatus();
    }

    @Override
    public QuestionClosingRemarkType component9() {
        return getClosingRemark();
    }

    @Override
    public Long component10() {
        return getAuthorId();
    }

    @Override
    public Long component11() {
        return getBountyId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getTitle();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public Integer value4() {
        return getViewCount();
    }

    @Override
    public Integer value5() {
        return getVoteCount();
    }

    @Override
    public Timestamp value6() {
        return getCreationTime();
    }

    @Override
    public Timestamp value7() {
        return getUpdateTime();
    }

    @Override
    public QuestionStatusType value8() {
        return getStatus();
    }

    @Override
    public QuestionClosingRemarkType value9() {
        return getClosingRemark();
    }

    @Override
    public Long value10() {
        return getAuthorId();
    }

    @Override
    public Long value11() {
        return getBountyId();
    }

    @Override
    public QuestionRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public QuestionRecord value2(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public QuestionRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public QuestionRecord value4(Integer value) {
        setViewCount(value);
        return this;
    }

    @Override
    public QuestionRecord value5(Integer value) {
        setVoteCount(value);
        return this;
    }

    @Override
    public QuestionRecord value6(Timestamp value) {
        setCreationTime(value);
        return this;
    }

    @Override
    public QuestionRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public QuestionRecord value8(QuestionStatusType value) {
        setStatus(value);
        return this;
    }

    @Override
    public QuestionRecord value9(QuestionClosingRemarkType value) {
        setClosingRemark(value);
        return this;
    }

    @Override
    public QuestionRecord value10(Long value) {
        setAuthorId(value);
        return this;
    }

    @Override
    public QuestionRecord value11(Long value) {
        setBountyId(value);
        return this;
    }

    @Override
    public QuestionRecord values(Long value1, String value2, String value3, Integer value4, Integer value5, Timestamp value6, Timestamp value7, QuestionStatusType value8, QuestionClosingRemarkType value9, Long value10, Long value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    public QuestionRecord() {
        super(QuestionTable.QUESTION_TABLE);
    }

    public QuestionRecord(Long id, String title, String description, Integer viewCount, Integer voteCount, Timestamp creationTime, Timestamp updateTime, QuestionStatusType status, QuestionClosingRemarkType closingRemark, Long authorId, Long bountyId) {
        super(QuestionTable.QUESTION_TABLE);

        set(0, id);
        set(1, title);
        set(2, description);
        set(3, viewCount);
        set(4, voteCount);
        set(5, creationTime);
        set(6, updateTime);
        set(7, status);
        set(8, closingRemark);
        set(9, authorId);
        set(10, bountyId);
    }
}
