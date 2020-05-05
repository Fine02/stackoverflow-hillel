package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberNotificationRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberNotificationTable extends TableImpl<MemberNotificationRecord> {

    private static final long serialVersionUID = -1310221333;
    public static final MemberNotificationTable MEMBER_NOTIFICATION_TABLE = new MemberNotificationTable();

    @Override
    public Class<MemberNotificationRecord> getRecordType() {
        return MemberNotificationRecord.class;
    }

    public static final TableField<MemberNotificationRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), MEMBER_NOTIFICATION_TABLE, "");
    public static final TableField<MemberNotificationRecord, Long> NOTIFICATION_ID = createField(DSL.name("notification_id"), BIGINT.nullable(false), MEMBER_NOTIFICATION_TABLE, "");

    private MemberNotificationTable() {
        this(DSL.name("member_notification"), null);
    }

    private MemberNotificationTable(Name alias, Table<MemberNotificationRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberNotificationTable(Name alias, Table<MemberNotificationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MemberNotificationTable(Table<O> child, ForeignKey<O, MemberNotificationRecord> key) {
        super(child, key, MEMBER_NOTIFICATION_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_NOTIFICATION_PKEY);
    }

    @Override
    public UniqueKey<MemberNotificationRecord> getPrimaryKey() {
        return Keys.MEMBER_NOTIFICATION_PKEY;
    }

    @Override
    public List<UniqueKey<MemberNotificationRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberNotificationRecord>>asList(Keys.MEMBER_NOTIFICATION_PKEY);
    }

    @Override
    public List<ForeignKey<MemberNotificationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MemberNotificationRecord, ?>>asList(Keys.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_ACCOUNT_ID, Keys.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_NOTIFICATION_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_ACCOUNT_ID);
    }

    public NotificationTable notification() {
        return new NotificationTable(this, Keys.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_NOTIFICATION_ID);
    }

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
