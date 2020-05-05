package com.ra.course.com.stackoverflow.entity.jooq;

import com.ra.course.com.stackoverflow.entity.jooq.tables.*;
import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 372070493;
    public static final Public PUBLIC = new Public();
    public final AccountTable ACCOUNT_TABLE = AccountTable.ACCOUNT_TABLE;
    public final AnswerTable ANSWER_TABLE = AnswerTable.ANSWER_TABLE;
    public final BountyTable BOUNTY_TABLE = BountyTable.BOUNTY_TABLE;
    public final CommentTable COMMENT_TABLE = CommentTable.COMMENT_TABLE;
    public final MemberBadgeQuestionTable MEMBER_BADGE_QUESTION_TABLE = MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE;
    public final MemberNotificationTable MEMBER_NOTIFICATION_TABLE = MemberNotificationTable.MEMBER_NOTIFICATION_TABLE;
    public final MemberVotedAnswerTable MEMBER_VOTED_ANSWER_TABLE = MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE;
    public final MemberVotedCommentTable MEMBER_VOTED_COMMENT_TABLE = MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE;
    public final MemberVotedQuestionTable MEMBER_VOTED_QUESTION_TABLE = MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE;
    public final NotificationTable NOTIFICATION_TABLE = NotificationTable.NOTIFICATION_TABLE;
    public final PhotoTable PHOTO_TABLE = PhotoTable.PHOTO_TABLE;
    public final QuestionTable QUESTION_TABLE = QuestionTable.QUESTION_TABLE;
    public final QuestionMemberQuestionClosingRemarkTable QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE = QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE;
    public final TagTable TAG_TABLE = TagTable.TAG_TABLE;
    public final TagQuestionTable TAG_QUESTION_TABLE = TagQuestionTable.TAG_QUESTION_TABLE;
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
            MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE,
            MemberNotificationTable.MEMBER_NOTIFICATION_TABLE,
            MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE,
            MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE,
            MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE,
            NotificationTable.NOTIFICATION_TABLE,
            PhotoTable.PHOTO_TABLE,
            QuestionTable.QUESTION_TABLE,
            QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE,
            TagTable.TAG_TABLE,
            TagQuestionTable.TAG_QUESTION_TABLE);
    }
}
