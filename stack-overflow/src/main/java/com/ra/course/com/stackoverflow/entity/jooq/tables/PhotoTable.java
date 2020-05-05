package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.PhotoRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PhotoTable extends TableImpl<PhotoRecord> {

    private static final long serialVersionUID = 750775289;
    public static final PhotoTable PHOTO_TABLE = new PhotoTable();

    @Override
    public Class<PhotoRecord> getRecordType() {
        return PhotoRecord.class;
    }

    public static final TableField<PhotoRecord, Long> ID = createField(DSL.name("id"), BIGINT.nullable(false).defaultValue(DSL.field("nextval('photo_id_seq'::regclass)", BIGINT)), PHOTO_TABLE, "");
    public static final TableField<PhotoRecord, String> PHOTO_PATH = createField(DSL.name("photo_path"), VARCHAR(1024).nullable(false), PHOTO_TABLE, "");
    public static final TableField<PhotoRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), TIMESTAMP.nullable(false), PHOTO_TABLE, "");
    public static final TableField<PhotoRecord, Long> QUESTION_ID = createField(DSL.name("question_id"), BIGINT, PHOTO_TABLE, "");
    public static final TableField<PhotoRecord, Long> ANSWER_ID = createField(DSL.name("answer_id"), BIGINT, PHOTO_TABLE, "");

    private PhotoTable() {
        this(DSL.name("photo"), null);
    }

    private PhotoTable(Name alias, Table<PhotoRecord> aliased) {
        this(alias, aliased, null);
    }

    private PhotoTable(Name alias, Table<PhotoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> PhotoTable(Table<O> child, ForeignKey<O, PhotoRecord> key) {
        super(child, key, PHOTO_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PHOTO_PKEY);
    }

    @Override
    public Identity<PhotoRecord, Long> getIdentity() {
        return Keys.IDENTITY_PHOTO;
    }

    @Override
    public UniqueKey<PhotoRecord> getPrimaryKey() {
        return Keys.PHOTO_PKEY;
    }

    @Override
    public List<UniqueKey<PhotoRecord>> getKeys() {
        return Arrays.<UniqueKey<PhotoRecord>>asList(Keys.PHOTO_PKEY);
    }

    @Override
    public List<ForeignKey<PhotoRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PhotoRecord, ?>>asList(Keys.PHOTO__FK_PHOTO_QUESTION_ID, Keys.PHOTO__FK_PHOTO_ANSWER_ID);
    }

    public QuestionTable question() {
        return new QuestionTable(this, Keys.PHOTO__FK_PHOTO_QUESTION_ID);
    }

    public AnswerTable answer() {
        return new AnswerTable(this, Keys.PHOTO__FK_PHOTO_ANSWER_ID);
    }

    @Override
    public Row5<Long, String, Timestamp, Long, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
