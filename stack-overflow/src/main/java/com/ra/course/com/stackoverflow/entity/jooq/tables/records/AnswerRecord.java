package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.AnswerTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnswerRecord extends UpdatableRecordImpl<AnswerRecord> implements Record8<Long, String, Boolean, Integer, Integer, Timestamp, Long, Long> {

    private static final long serialVersionUID = 1412897030;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setAnswerText(String value) {
        set(1, value);
    }

    public String getAnswerText() {
        return (String) get(1);
    }

    public void setAccepted(Boolean value) {
        set(2, value);
    }

    public Boolean getAccepted() {
        return (Boolean) get(2);
    }

    public void setVoteCount(Integer value) {
        set(3, value);
    }

    public Integer getVoteCount() {
        return (Integer) get(3);
    }

    public void setFlagCount(Integer value) {
        set(4, value);
    }

    public Integer getFlagCount() {
        return (Integer) get(4);
    }

    public void setCreationDate(Timestamp value) {
        set(5, value);
    }

    public Timestamp getCreationDate() {
        return (Timestamp) get(5);
    }

    public void setAuthorId(Long value) {
        set(6, value);
    }

    public Long getAuthorId() {
        return (Long) get(6);
    }

    public void setQuestionId(Long value) {
        set(7, value);
    }

    public Long getQuestionId() {
        return (Long) get(7);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row8<Long, String, Boolean, Integer, Integer, Timestamp, Long, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Long, String, Boolean, Integer, Integer, Timestamp, Long, Long> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return AnswerTable.ID;
    }

    @Override
    public Field<String> field2() {
        return AnswerTable.ANSWER_TEXT;
    }

    @Override
    public Field<Boolean> field3() {
        return AnswerTable.ACCEPTED;
    }

    @Override
    public Field<Integer> field4() {
        return AnswerTable.VOTE_COUNT;
    }

    @Override
    public Field<Integer> field5() {
        return AnswerTable.FLAG_COUNT;
    }

    @Override
    public Field<Timestamp> field6() {
        return AnswerTable.CREATION_DATE;
    }

    @Override
    public Field<Long> field7() {
        return AnswerTable.AUTHOR_ID;
    }

    @Override
    public Field<Long> field8() {
        return AnswerTable.QUESTION_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getAnswerText();
    }

    @Override
    public Boolean component3() {
        return getAccepted();
    }

    @Override
    public Integer component4() {
        return getVoteCount();
    }

    @Override
    public Integer component5() {
        return getFlagCount();
    }

    @Override
    public Timestamp component6() {
        return getCreationDate();
    }

    @Override
    public Long component7() {
        return getAuthorId();
    }

    @Override
    public Long component8() {
        return getQuestionId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getAnswerText();
    }

    @Override
    public Boolean value3() {
        return getAccepted();
    }

    @Override
    public Integer value4() {
        return getVoteCount();
    }

    @Override
    public Integer value5() {
        return getFlagCount();
    }

    @Override
    public Timestamp value6() {
        return getCreationDate();
    }

    @Override
    public Long value7() {
        return getAuthorId();
    }

    @Override
    public Long value8() {
        return getQuestionId();
    }

    @Override
    public AnswerRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public AnswerRecord value2(String value) {
        setAnswerText(value);
        return this;
    }

    @Override
    public AnswerRecord value3(Boolean value) {
        setAccepted(value);
        return this;
    }

    @Override
    public AnswerRecord value4(Integer value) {
        setVoteCount(value);
        return this;
    }

    @Override
    public AnswerRecord value5(Integer value) {
        setFlagCount(value);
        return this;
    }

    @Override
    public AnswerRecord value6(Timestamp value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public AnswerRecord value7(Long value) {
        setAuthorId(value);
        return this;
    }

    @Override
    public AnswerRecord value8(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public AnswerRecord values(Long value1, String value2, Boolean value3, Integer value4, Integer value5, Timestamp value6, Long value7, Long value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    public AnswerRecord() {
        super(AnswerTable.ANSWER_TABLE);
    }

    public AnswerRecord(Long id, String answerText, Boolean accepted, Integer voteCount, Integer flagCount, Timestamp creationDate, Long authorId, Long questionId) {
        super(AnswerTable.ANSWER_TABLE);

        set(0, id);
        set(1, answerText);
        set(2, accepted);
        set(3, voteCount);
        set(4, flagCount);
        set(5, creationDate);
        set(6, authorId);
        set(7, questionId);
    }
}
