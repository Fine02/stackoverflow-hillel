package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.NotificationTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotificationRecord extends UpdatableRecordImpl<NotificationRecord> implements Record3<Long, Timestamp, String> {

    private static final long serialVersionUID = -414486266;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setCreatedOn(Timestamp value) {
        set(1, value);
    }

    public Timestamp getCreatedOn() {
        return (Timestamp) get(1);
    }

    public void setContent(String value) {
        set(2, value);
    }

    public String getContent() {
        return (String) get(2);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row3<Long, Timestamp, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Timestamp, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return NotificationTable.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return NotificationTable.CREATED_ON;
    }

    @Override
    public Field<String> field3() {
        return NotificationTable.CONTENT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getCreatedOn();
    }

    @Override
    public String component3() {
        return getContent();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Timestamp value2() {
        return getCreatedOn();
    }

    @Override
    public String value3() {
        return getContent();
    }

    @Override
    public NotificationRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public NotificationRecord value2(Timestamp value) {
        setCreatedOn(value);
        return this;
    }

    @Override
    public NotificationRecord value3(String value) {
        setContent(value);
        return this;
    }

    @Override
    public NotificationRecord values(Long value1, Timestamp value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    public NotificationRecord() {
        super(NotificationTable.NOTIFICATION_TABLE);
    }

    public NotificationRecord(Long id, Timestamp createdOn, String content) {
        super(NotificationTable.NOTIFICATION_TABLE);

        set(0, id);
        set(1, createdOn);
        set(2, content);
    }
}
