package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.BountyRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BountyTable extends TableImpl<BountyRecord> {

    private static final long serialVersionUID = -2144393609;
    public static final BountyTable BOUNTY_TABLE = new BountyTable();

    @Override
    public Class<BountyRecord> getRecordType() {
        return BountyRecord.class;
    }

    public static final TableField<BountyRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('bounty_id_seq'::regclass)", BIGINT)), BOUNTY_TABLE, "");
    public static final TableField<BountyRecord, Integer> REPUTATION = createField(DSL.name("reputation"), INTEGER, BOUNTY_TABLE, "");
    public static final TableField<BountyRecord, Timestamp> EXPIRY = createField(DSL.name("expiry"), TIMESTAMP.nullable(false), BOUNTY_TABLE, "");
    public static final TableField<BountyRecord, Long> CREATOR_ID = createField(DSL.name("creator_id"), BIGINT.nullable(false), BOUNTY_TABLE, "");

    private BountyTable() {
        this(DSL.name("bounty"), null);
    }

    private BountyTable(Name alias, Table<BountyRecord> aliased) {
        this(alias, aliased, null);
    }

    private BountyTable(Name alias, Table<BountyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> BountyTable(Table<O> child, ForeignKey<O, BountyRecord> key) {
        super(child, key, BOUNTY_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.BOUNTY_PKEY);
    }

    @Override
    public Identity<BountyRecord, Long> getIdentity() {
        return Keys.IDENTITY_BOUNTY;
    }

    @Override
    public UniqueKey<BountyRecord> getPrimaryKey() {
        return Keys.BOUNTY_PKEY;
    }

    @Override
    public List<UniqueKey<BountyRecord>> getKeys() {
        return Arrays.<UniqueKey<BountyRecord>>asList(Keys.BOUNTY_PKEY);
    }

    @Override
    public List<ForeignKey<BountyRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<BountyRecord, ?>>asList(Keys.BOUNTY__FK_CREATOR_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.BOUNTY__FK_CREATOR_ID);
    }

    @Override
    public Row4<Long, Integer, Timestamp, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
