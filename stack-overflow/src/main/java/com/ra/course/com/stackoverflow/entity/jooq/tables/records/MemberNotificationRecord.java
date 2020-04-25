package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.MemberNotificationTable;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberNotificationRecord extends UpdatableRecordImpl<MemberNotificationRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = -1979388329;

    public void setAccountId(Long value) {
        set(0, value);
    }

    public Long getAccountId() {
        return (Long) get(0);
    }

    public void setNotificationId(Long value) {
        set(1, value);
    }

    public Long getNotificationId() {
        return (Long) get(1);
    }

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MemberNotificationTable.ACCOUNT_ID;
    }

    @Override
    public Field<Long> field2() {
        return MemberNotificationTable.NOTIFICATION_ID;
    }

    @Override
    public Long component1() {
        return getAccountId();
    }

    @Override
    public Long component2() {
        return getNotificationId();
    }

    @Override
    public Long value1() {
        return getAccountId();
    }

    @Override
    public Long value2() {
        return getNotificationId();
    }

    @Override
    public MemberNotificationRecord value1(Long value) {
        setAccountId(value);
        return this;
    }

    @Override
    public MemberNotificationRecord value2(Long value) {
        setNotificationId(value);
        return this;
    }

    @Override
    public MemberNotificationRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    public MemberNotificationRecord() {
        super(MemberNotificationTable.MEMBER_NOTIFICATION_TABLE);
    }

    public MemberNotificationRecord(Long accountId, Long notificationId) {
        super(MemberNotificationTable.MEMBER_NOTIFICATION_TABLE);

        set(0, accountId);
        set(1, notificationId);
    }
}
