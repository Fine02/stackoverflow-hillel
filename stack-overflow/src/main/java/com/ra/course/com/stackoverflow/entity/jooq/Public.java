/*
 * This file is generated by jOOQ.
 */
package com.ra.course.com.stackoverflow.entity.jooq;


import com.ra.course.com.stackoverflow.entity.jooq.tables.*;
import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -1311895871;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.account</code>.
     */
    public final AccountTable ACCOUNT_TABLE = AccountTable.ACCOUNT_TABLE;

    /**
     * The table <code>public.answer</code>.
     */
    public final AnswerTable ANSWER_TABLE = AnswerTable.ANSWER_TABLE;

    /**
     * The table <code>public.bounty</code>.
     */
    public final BountyTable BOUNTY_TABLE = BountyTable.BOUNTY_TABLE;

    /**
     * The table <code>public.comment</code>.
     */
    public final CommentTable COMMENT_TABLE = CommentTable.COMMENT_TABLE;

    /**
     * The table <code>public.member</code>.
     */
    public final MemberTable MEMBER_TABLE = MemberTable.MEMBER_TABLE;

    /**
     * The table <code>public.notification</code>.
     */
    public final NotificationTable NOTIFICATION_TABLE = NotificationTable.NOTIFICATION_TABLE;

    /**
     * The table <code>public.photo</code>.
     */
    public final PhotoTable PHOTO_TABLE = PhotoTable.PHOTO_TABLE;

    /**
     * The table <code>public.question</code>.
     */
    public final QuestionTable QUESTION_TABLE = QuestionTable.QUESTION_TABLE;

    /**
     * The table <code>public.tag</code>.
     */
    public final TagTable TAG_TABLE = TagTable.TAG_TABLE;

    /**
     * The table <code>public.tag_question</code>.
     */
    public final TagQuestionTable TAG_QUESTION = TagQuestionTable.TAG_QUESTION_TABLE;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ACCOUNT_ID_SEQ,
            Sequences.ANSWER_ID_SEQ,
            Sequences.BOUNTY_ID_SEQ,
            Sequences.COMMENT_ID_SEQ,
            Sequences.MEMBER_ID_SEQ,
            Sequences.NOTIFICATION_ID_SEQ,
            Sequences.PHOTO_ID_SEQ,
            Sequences.QUESTION_ID_SEQ,
            Sequences.TAG_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            AccountTable.ACCOUNT_TABLE,
            AnswerTable.ANSWER_TABLE,
            BountyTable.BOUNTY_TABLE,
            CommentTable.COMMENT_TABLE,
            MemberTable.MEMBER_TABLE,
            NotificationTable.NOTIFICATION_TABLE,
            PhotoTable.PHOTO_TABLE,
            QuestionTable.QUESTION_TABLE,
            TagTable.TAG_TABLE,
            TagQuestionTable.TAG_QUESTION_TABLE);
    }
}
