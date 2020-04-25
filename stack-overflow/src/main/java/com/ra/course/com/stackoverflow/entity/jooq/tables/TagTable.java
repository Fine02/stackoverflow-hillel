package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.TagRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagTable extends TableImpl<TagRecord> {

    private static final long serialVersionUID = -1963728011;
    public static final TagTable TAG_TABLE = new TagTable();

    @Override
    public Class<TagRecord> getRecordType() {
        return TagRecord.class;
    }

    public static final TableField<TagRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('tag_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), TAG_TABLE, "");
    public static final TableField<TagRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), TAG_TABLE, "");
    public static final TableField<TagRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(255), TAG_TABLE, "");

    private TagTable() {
        this(DSL.name("tag"), null);
    }

    private TagTable(Name alias, Table<TagRecord> aliased) {
        this(alias, aliased, null);
    }

    private TagTable(Name alias, Table<TagRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> TagTable(Table<O> child, ForeignKey<O, TagRecord> key) {
        super(child, key, TAG_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.AK_TAG_NAME, Indexes.TAG_PKEY);
    }

    @Override
    public Identity<TagRecord, Long> getIdentity() {
        return Keys.IDENTITY_TAG;
    }

    @Override
    public UniqueKey<TagRecord> getPrimaryKey() {
        return Keys.TAG_PKEY;
    }

    @Override
    public List<UniqueKey<TagRecord>> getKeys() {
        return Arrays.<UniqueKey<TagRecord>>asList(Keys.TAG_PKEY, Keys.AK_TAG_NAME);
    }

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
