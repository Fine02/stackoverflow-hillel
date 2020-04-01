/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq.tables;


import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.NotificationRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NotificationTable extends TableImpl<NotificationRecord> {

    private static final long serialVersionUID = 1694954093;

    /**
     * The reference instance of <code>public.notification</code>
     */
    public static final NotificationTable NOTIFICATION_TABLE = new NotificationTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NotificationRecord> getRecordType() {
        return NotificationRecord.class;
    }

    /**
     * The column <code>public.notification.id</code>.
     */
    public static final TableField<NotificationRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('notification_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), NOTIFICATION_TABLE, "");

    /**
     * The column <code>public.notification.created_on</code>.
     */
    public static final TableField<NotificationRecord, Timestamp> CREATED_ON = createField(DSL.name("created_on"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), NOTIFICATION_TABLE, "");

    /**
     * The column <code>public.notification.content</code>.
     */
    public static final TableField<NotificationRecord, String> CONTENT = createField(DSL.name("content"), org.jooq.impl.SQLDataType.CLOB.nullable(false), NOTIFICATION_TABLE, "");

    /**
     * No further instances allowed
     */
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

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Timestamp, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
