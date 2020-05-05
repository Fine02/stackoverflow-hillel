package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.enums.AccountStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AccountRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountTable extends TableImpl<AccountRecord> {

    private static final long serialVersionUID = -399927949;
    public static final AccountTable ACCOUNT_TABLE = new AccountTable();

    @Override
    public Class<AccountRecord> getRecordType() {
        return AccountRecord.class;
    }

    public static final TableField<AccountRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('account_id_seq'::regclass)", BIGINT)), ACCOUNT_TABLE, "");
    public static final TableField<AccountRecord, String> PASSWORD = createField(DSL.name("password"), VARCHAR(45).nullable(false), ACCOUNT_TABLE, "");
    public static final TableField<AccountRecord, String> NAME = createField(DSL.name("name"), VARCHAR(45).nullable(false), ACCOUNT_TABLE, "");
    public static final TableField<AccountRecord, String> EMAIL = createField(DSL.name("email"), VARCHAR(45).nullable(false), ACCOUNT_TABLE, "");
    public static final TableField<AccountRecord, Integer> REPUTATION = createField(DSL.name("reputation"), INTEGER, ACCOUNT_TABLE, "");
    public static final TableField<AccountRecord, AccountStatusType> ACCOUNT_STATUS = createField(DSL.name("account_status"), VARCHAR.asEnumDataType(AccountStatusType.class), ACCOUNT_TABLE, "");

    private AccountTable() {
        this(DSL.name("account"), null);
    }

    private AccountTable(Name alias, Table<AccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private AccountTable(Name alias, Table<AccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AccountTable(Table<O> child, ForeignKey<O, AccountRecord> key) {
        super(child, key, ACCOUNT_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ACCOUNT_PKEY, Indexes.AK_ACCOUNT_EMAIL);
    }

    @Override
    public Identity<AccountRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACCOUNT;
    }

    @Override
    public UniqueKey<AccountRecord> getPrimaryKey() {
        return Keys.ACCOUNT_PKEY;
    }

    @Override
    public List<UniqueKey<AccountRecord>> getKeys() {
        return Arrays.<UniqueKey<AccountRecord>>asList(Keys.ACCOUNT_PKEY, Keys.AK_ACCOUNT_EMAIL);
    }

    @Override
    public Row6<Long, String, String, String, Integer, AccountStatusType> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
