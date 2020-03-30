/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq.tables.records;


import com.ra.course.com.stackoverflow.entity.jooq.tables.QuestionClosingRemarkTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionClosingRemarkRecord extends UpdatableRecordImpl<QuestionClosingRemarkRecord> implements Record2<Long, String> {

    private static final long serialVersionUID = -416313898;

    /**
     * Setter for <code>public.question_closing_remark.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.question_closing_remark.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.question_closing_remark.remark</code>.
     */
    public void setRemark(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.question_closing_remark.remark</code>.
     */
    public String getRemark() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return QuestionClosingRemarkTable.QUESTION_CLOSING_REMARK_TABLE.ID;
    }

    @Override
    public Field<String> field2() {
        return QuestionClosingRemarkTable.QUESTION_CLOSING_REMARK_TABLE.REMARK;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getRemark();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getRemark();
    }

    @Override
    public QuestionClosingRemarkRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public QuestionClosingRemarkRecord value2(String value) {
        setRemark(value);
        return this;
    }

    @Override
    public QuestionClosingRemarkRecord values(Long value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QuestionClosingRemarkRecord
     */
    public QuestionClosingRemarkRecord() {
        super(QuestionClosingRemarkTable.QUESTION_CLOSING_REMARK_TABLE);
    }

    /**
     * Create a detached, initialised QuestionClosingRemarkRecord
     */
    public QuestionClosingRemarkRecord(Long id, String remark) {
        super(QuestionClosingRemarkTable.QUESTION_CLOSING_REMARK_TABLE);

        set(0, id);
        set(1, remark);
    }
}
