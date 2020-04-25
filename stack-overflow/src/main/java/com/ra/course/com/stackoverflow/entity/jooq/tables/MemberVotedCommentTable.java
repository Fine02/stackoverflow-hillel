package com.ra.course.com.stackoverflow.entity.jooq.tables;

import com.ra.course.com.stackoverflow.entity.jooq.Indexes;
import com.ra.course.com.stackoverflow.entity.jooq.Keys;
import com.ra.course.com.stackoverflow.entity.jooq.Public;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedCommentRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

import static org.jooq.impl.SQLDataType.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberVotedCommentTable extends TableImpl<MemberVotedCommentRecord> {

    private static final long serialVersionUID = 2101011576;
    public static final MemberVotedCommentTable MEMBER_VOTED_COMMENT_TABLE = new MemberVotedCommentTable();

    @Override
    public Class<MemberVotedCommentRecord> getRecordType() {
        return MemberVotedCommentRecord.class;
    }

    public static final TableField<MemberVotedCommentRecord, Long> ACCOUNT_ID = createField(DSL.name("account_id"), BIGINT.nullable(false), MEMBER_VOTED_COMMENT_TABLE, "");
    public static final TableField<MemberVotedCommentRecord, Long> COMMENT_ID = createField(DSL.name("comment_id"), BIGINT.nullable(false), MEMBER_VOTED_COMMENT_TABLE, "");
    public static final TableField<MemberVotedCommentRecord, Boolean> UPVOTED = createField(DSL.name("upvoted"), BOOLEAN, MEMBER_VOTED_COMMENT_TABLE, "");

    private MemberVotedCommentTable() {
        this(DSL.name("member_voted_comment"), null);
    }

    private MemberVotedCommentTable(Name alias, Table<MemberVotedCommentRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberVotedCommentTable(Name alias, Table<MemberVotedCommentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MemberVotedCommentTable(Table<O> child, ForeignKey<O, MemberVotedCommentRecord> key) {
        super(child, key, MEMBER_VOTED_COMMENT_TABLE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_VOTED_COMMENT_PKEY);
    }

    @Override
    public UniqueKey<MemberVotedCommentRecord> getPrimaryKey() {
        return Keys.MEMBER_VOTED_COMMENT_PKEY;
    }

    @Override
    public List<UniqueKey<MemberVotedCommentRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberVotedCommentRecord>>asList(Keys.MEMBER_VOTED_COMMENT_PKEY);
    }

    @Override
    public List<ForeignKey<MemberVotedCommentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<MemberVotedCommentRecord, ?>>asList(Keys.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_ACCOUNT_ID, Keys.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_COMMENT_ID);
    }

    public AccountTable account() {
        return new AccountTable(this, Keys.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_ACCOUNT_ID);
    }

    public CommentTable comment() {
        return new CommentTable(this, Keys.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_COMMENT_ID);
    }

    @Override
    public Row3<Long, Long, Boolean> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
