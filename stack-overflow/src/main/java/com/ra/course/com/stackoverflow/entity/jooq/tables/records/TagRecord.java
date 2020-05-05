package com.ra.course.com.stackoverflow.entity.jooq.tables.records;

import com.ra.course.com.stackoverflow.entity.jooq.tables.TagTable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagRecord extends UpdatableRecordImpl<TagRecord> implements Record3<Long, String, String> {

    private static final long serialVersionUID = 809219955;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setName(String value) {
        set(1, value);
    }

    public String getName() {
        return (String) get(1);
    }

    public void setDescription(String value) {
        set(2, value);
    }

    public String getDescription() {
        return (String) get(2);
    }

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return TagTable.ID;
    }

    @Override
    public Field<String> field2() {
        return TagTable.NAME;
    }

    @Override
    public Field<String> field3() {
        return TagTable.DESCRIPTION;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public TagRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public TagRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public TagRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public TagRecord values(Long value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    public TagRecord() {
        super(TagTable.TAG_TABLE);
    }

    public TagRecord(Long id, String name, String description) {
        super(TagTable.TAG_TABLE);

        set(0, id);
        set(1, name);
        set(2, description);
    }
}
