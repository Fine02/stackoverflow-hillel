package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Timestamp;

@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class BountyRecord extends UpdatableRecordImpl<BountyRecord> implements Record4<Long, Integer, Timestamp, Long> {

    private static final long serialVersionUID = 110465485;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setReputation(Integer value) {
        set(1, value);
    }

    public Integer getReputation() {
        return (Integer) get(1);
    }

    public void setExpiry(Timestamp value) {
        set(2, value);
    }

    public Timestamp getExpiry() {
        return (Timestamp) get(2);
    }

    public void setCreatorId(Long value) {
        set(3, value);
    }

    public Long getCreatorId() {
        return (Long) get(3);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row4<Long, Integer, Timestamp, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Integer, Timestamp, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return BountyTable.ID;
    }

    @Override
    public Field<Integer> field2() {
        return BountyTable.REPUTATION;
    }

    @Override
    public Field<Timestamp> field3() {
        return BountyTable.EXPIRY;
    }

    @Override
    public Field<Long> field4() {
        return BountyTable.CREATOR_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getReputation();
    }

    @Override
    public Timestamp component3() {
        return getExpiry();
    }

    @Override
    public Long component4() {
        return getCreatorId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getReputation();
    }

    @Override
    public Timestamp value3() {
        return getExpiry();
    }

    @Override
    public Long value4() {
        return getCreatorId();
    }

    @Override
    public BountyRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public BountyRecord value2(Integer value) {
        setReputation(value);
        return this;
    }

    @Override
    public BountyRecord value3(Timestamp value) {
        setExpiry(value);
        return this;
    }

    @Override
    public BountyRecord value4(Long value) {
        setCreatorId(value);
        return this;
    }

    @Override
    public BountyRecord values(Long value1, Integer value2, Timestamp value3, Long value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    public BountyRecord() {
        super(BountyTable.BOUNTY_TABLE);
    }

    public BountyRecord(Long id, Integer reputation, Timestamp expiry, Long creatorId) {
        super(BountyTable.BOUNTY_TABLE);

        set(0, id);
        set(1, reputation);
        set(2, expiry);
        set(3, creatorId);
    }
}
