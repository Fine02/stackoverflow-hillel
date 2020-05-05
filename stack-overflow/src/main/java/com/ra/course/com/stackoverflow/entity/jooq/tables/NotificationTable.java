package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.NotificationRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotificationTable extends TableImpl<NotificationRecord> {

    private static final long serialVersionUID = 1694954093;
    public static final NotificationTable NOTIFICATION_TABLE = new NotificationTable();

    @Override
    public Class<NotificationRecord> getRecordType() {
        return NotificationRecord.class;
    }

    public static final TableField<NotificationRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('notification_id_seq'::regclass)", BIGINT)), NOTIFICATION_TABLE, "");
    public static final TableField<NotificationRecord, Timestamp> CREATED_ON = createField(DSL.name("created_on"), TIMESTAMP.nullable(false), NOTIFICATION_TABLE, "");
    public static final TableField<NotificationRecord, String> CONTENT = createField(DSL.name("content"), CLOB.nullable(false), NOTIFICATION_TABLE, "");

    private NotificationTable() {
        this(DSL.name("notification"), null);
    }

    private NotificationTable(Name alias, Table<NotificationRecord> aliased) {
        this(alias, aliased, null);
    }

    private NotificationTable(Name alias, Table<NotificationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> NotificationTable(Table<O> child, ForeignKey<O, NotificationRecord> key) {
        super(child, key, NOTIFICATION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.NOTIFICATION_PKEY);
    }

    @Override
    public Identity<NotificationRecord, Long> getIdentity() {
        return Keys.IDENTITY_NOTIFICATION;
    }

    @Override
    public UniqueKey<NotificationRecord> getPrimaryKey() {
        return Keys.NOTIFICATION_PKEY;
    }

    @Override
    public List<UniqueKey<NotificationRecord>> getKeys() {
        return Arrays.<UniqueKey<NotificationRecord>>asList(Keys.NOTIFICATION_PKEY);
    }

    @Override
    public Row3<Long, Timestamp, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
