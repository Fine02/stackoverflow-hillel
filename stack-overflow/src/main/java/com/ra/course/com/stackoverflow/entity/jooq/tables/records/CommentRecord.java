package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.CommentTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CommentRecord extends UpdatableRecordImpl<CommentRecord> implements Record7<Long, String, Timestamp, Integer, Long, Long, Long> {

    private static final long serialVersionUID = 1496193783;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setCommentText(String value) {
        set(1, value);
    }

    public String getCommentText() {
        return (String) get(1);
    }

    public void setCreationDate(Timestamp value) {
        set(2, value);
    }

    public Timestamp getCreationDate() {
        return (Timestamp) get(2);
    }

    public void setVoteCount(Integer value) {
        set(3, value);
    }

    public Integer getVoteCount() {
        return (Integer) get(3);
    }

    public void setAuthorId(Long value) {
        set(4, value);
    }

    public Long getAuthorId() {
        return (Long) get(4);
    }

    public void setAnswerId(Long value) {
        set(5, value);
    }

    public Long getAnswerId() {
        return (Long) get(5);
    }

    public void setQuestionId(Long value) {
        set(6, value);
    }

    public Long getQuestionId() {
        return (Long) get(6);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row7<Long, String, Timestamp, Integer, Long, Long, Long> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, String, Timestamp, Integer, Long, Long, Long> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return CommentTable.ID;
    }

    @Override
    public Field<String> field2() {
        return CommentTable.COMMENT_TEXT;
    }

    @Override
    public Field<Timestamp> field3() {
        return CommentTable.CREATION_DATE;
    }

    @Override
    public Field<Integer> field4() {
        return CommentTable.VOTE_COUNT;
    }

    @Override
    public Field<Long> field5() {
        return CommentTable.AUTHOR_ID;
    }

    @Override
    public Field<Long> field6() {
        return CommentTable.ANSWER_ID;
    }

    @Override
    public Field<Long> field7() {
        return CommentTable.QUESTION_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getCommentText();
    }

    @Override
    public Timestamp component3() {
        return getCreationDate();
    }

    @Override
    public Integer component4() {
        return getVoteCount();
    }

    @Override
    public Long component5() {
        return getAuthorId();
    }

    @Override
    public Long component6() {
        return getAnswerId();
    }

    @Override
    public Long component7() {
        return getQuestionId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getCommentText();
    }

    @Override
    public Timestamp value3() {
        return getCreationDate();
    }

    @Override
    public Integer value4() {
        return getVoteCount();
    }

    @Override
    public Long value5() {
        return getAuthorId();
    }

    @Override
    public Long value6() {
        return getAnswerId();
    }

    @Override
    public Long value7() {
        return getQuestionId();
    }

    @Override
    public CommentRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public CommentRecord value2(String value) {
        setCommentText(value);
        return this;
    }

    @Override
    public CommentRecord value3(Timestamp value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public CommentRecord value4(Integer value) {
        setVoteCount(value);
        return this;
    }

    @Override
    public CommentRecord value5(Long value) {
        setAuthorId(value);
        return this;
    }

    @Override
    public CommentRecord value6(Long value) {
        setAnswerId(value);
        return this;
    }

    @Override
    public CommentRecord value7(Long value) {
        setQuestionId(value);
        return this;
    }

    @Override
    public CommentRecord values(Long value1, String value2, Timestamp value3, Integer value4, Long value5, Long value6, Long value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    public CommentRecord() {
        super(CommentTable.COMMENT_TABLE);
    }

    public CommentRecord(Long id, String commentText, Timestamp creationDate, Integer voteCount, Long authorId, Long answerId, Long questionId) {
        super(CommentTable.COMMENT_TABLE);

        set(0, id);
        set(1, commentText);
        set(2, creationDate);
        set(3, voteCount);
        set(4, authorId);
        set(5, answerId);
        set(6, questionId);
    }
}
