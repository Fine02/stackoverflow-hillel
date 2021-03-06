package com.ra.course.com.stackoverflow.entity.jooq;

import com.ra.course.com.stackoverflow.entity.jooq.tables.*;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    public static final Index ACCOUNT_PKEY = Indexes0.ACCOUNT_PKEY;
    public static final Index AK_ACCOUNT_EMAIL = Indexes0.AK_ACCOUNT_EMAIL;
    public static final Index ANSWER_PKEY = Indexes0.ANSWER_PKEY;
    public static final Index BOUNTY_PKEY = Indexes0.BOUNTY_PKEY;
    public static final Index COMMENT_PKEY = Indexes0.COMMENT_PKEY;
    public static final Index MEMBER_BADGE_QUESTION_PKEY = Indexes0.MEMBER_BADGE_QUESTION_PKEY;
    public static final Index MEMBER_NOTIFICATION_PKEY = Indexes0.MEMBER_NOTIFICATION_PKEY;
    public static final Index MEMBER_VOTED_ANSWER_PKEY = Indexes0.MEMBER_VOTED_ANSWER_PKEY;
    public static final Index MEMBER_VOTED_COMMENT_PKEY = Indexes0.MEMBER_VOTED_COMMENT_PKEY;
    public static final Index MEMBER_VOTED_QUESTION_PKEY = Indexes0.MEMBER_VOTED_QUESTION_PKEY;
    public static final Index NOTIFICATION_PKEY = Indexes0.NOTIFICATION_PKEY;
    public static final Index PHOTO_PKEY = Indexes0.PHOTO_PKEY;
    public static final Index QUESTION_PKEY = Indexes0.QUESTION_PKEY;
    public static final Index QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY = Indexes0.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY;
    public static final Index AK_TAG_NAME = Indexes0.AK_TAG_NAME;
    public static final Index TAG_PKEY = Indexes0.TAG_PKEY;

    private static class Indexes0 {
        public static Index ACCOUNT_PKEY = Internal.createIndex("account_pkey", AccountTable.ACCOUNT_TABLE, new OrderField[] { AccountTable.ID }, true);
        public static Index AK_ACCOUNT_EMAIL = Internal.createIndex("ak_account_email", AccountTable.ACCOUNT_TABLE, new OrderField[] { AccountTable.EMAIL }, true);
        public static Index ANSWER_PKEY = Internal.createIndex("answer_pkey", AnswerTable.ANSWER_TABLE, new OrderField[] { AnswerTable.ID }, true);
        public static Index BOUNTY_PKEY = Internal.createIndex("bounty_pkey", BountyTable.BOUNTY_TABLE, new OrderField[] { BountyTable.ID }, true);
        public static Index COMMENT_PKEY = Internal.createIndex("comment_pkey", CommentTable.COMMENT_TABLE, new OrderField[] { CommentTable.ID }, true);
        public static Index MEMBER_BADGE_QUESTION_PKEY = Internal.createIndex("member_badge_question_pkey", MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE, new OrderField[] { MemberBadgeQuestionTable.ACCOUNT_ID, MemberBadgeQuestionTable.BADGE, MemberBadgeQuestionTable.QUESTION_ID }, true);
        public static Index MEMBER_NOTIFICATION_PKEY = Internal.createIndex("member_notification_pkey", MemberNotificationTable.MEMBER_NOTIFICATION_TABLE, new OrderField[] { MemberNotificationTable.ACCOUNT_ID, MemberNotificationTable.NOTIFICATION_ID }, true);
        public static Index MEMBER_VOTED_ANSWER_PKEY = Internal.createIndex("member_voted_answer_pkey", MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE, new OrderField[] { MemberVotedAnswerTable.ACCOUNT_ID, MemberVotedAnswerTable.ANSWER_ID }, true);
        public static Index MEMBER_VOTED_COMMENT_PKEY = Internal.createIndex("member_voted_comment_pkey", MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE, new OrderField[] { MemberVotedCommentTable.ACCOUNT_ID, MemberVotedCommentTable.COMMENT_ID }, true);
        public static Index MEMBER_VOTED_QUESTION_PKEY = Internal.createIndex("member_voted_question_pkey", MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE, new OrderField[] { MemberVotedQuestionTable.ACCOUNT_ID, MemberVotedQuestionTable.QUESTION_ID }, true);
        public static Index NOTIFICATION_PKEY = Internal.createIndex("notification_pkey", NotificationTable.NOTIFICATION_TABLE, new OrderField[] { NotificationTable.ID }, true);
        public static Index PHOTO_PKEY = Internal.createIndex("photo_pkey", PhotoTable.PHOTO_TABLE, new OrderField[] { PhotoTable.ID }, true);
        public static Index QUESTION_PKEY = Internal.createIndex("question_pkey", QuestionTable.QUESTION_TABLE, new OrderField[] { QuestionTable.ID }, true);
        public static Index QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY = Internal.createIndex("question_member_question_closing_remark_pkey", QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, new OrderField[] { QuestionMemberQuestionClosingRemarkTable.QUESTION_ID, QuestionMemberQuestionClosingRemarkTable.ACCOUNT_ID, QuestionMemberQuestionClosingRemarkTable.CLOSING_REMARK }, true);
        public static Index AK_TAG_NAME = Internal.createIndex("ak_tag_name", TagTable.TAG_TABLE, new OrderField[] { TagTable.NAME }, true);
        public static Index TAG_PKEY = Internal.createIndex("tag_pkey", TagTable.TAG_TABLE, new OrderField[] { TagTable.ID }, true);
    }
}
