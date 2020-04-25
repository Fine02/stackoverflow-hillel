package com.ra.course.com.stackoverflow.entity.jooq;

import com.ra.course.com.stackoverflow.entity.jooq.tables.*;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.*;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    public static final Identity<AccountRecord, Long> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
    public static final Identity<AnswerRecord, Long> IDENTITY_ANSWER = Identities0.IDENTITY_ANSWER;
    public static final Identity<BountyRecord, Long> IDENTITY_BOUNTY = Identities0.IDENTITY_BOUNTY;
    public static final Identity<CommentRecord, Long> IDENTITY_COMMENT = Identities0.IDENTITY_COMMENT;
    public static final Identity<NotificationRecord, Long> IDENTITY_NOTIFICATION = Identities0.IDENTITY_NOTIFICATION;
    public static final Identity<PhotoRecord, Long> IDENTITY_PHOTO = Identities0.IDENTITY_PHOTO;
    public static final Identity<QuestionRecord, Long> IDENTITY_QUESTION = Identities0.IDENTITY_QUESTION;
    public static final Identity<TagRecord, Long> IDENTITY_TAG = Identities0.IDENTITY_TAG;

    public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = UniqueKeys0.ACCOUNT_PKEY;
    public static final UniqueKey<AccountRecord> AK_ACCOUNT_EMAIL = UniqueKeys0.AK_ACCOUNT_EMAIL;
    public static final UniqueKey<AnswerRecord> ANSWER_PKEY = UniqueKeys0.ANSWER_PKEY;
    public static final UniqueKey<BountyRecord> BOUNTY_PKEY = UniqueKeys0.BOUNTY_PKEY;
    public static final UniqueKey<CommentRecord> COMMENT_PKEY = UniqueKeys0.COMMENT_PKEY;
    public static final UniqueKey<MemberBadgeQuestionRecord> MEMBER_BADGE_QUESTION_PKEY = UniqueKeys0.MEMBER_BADGE_QUESTION_PKEY;
    public static final UniqueKey<MemberNotificationRecord> MEMBER_NOTIFICATION_PKEY = UniqueKeys0.MEMBER_NOTIFICATION_PKEY;
    public static final UniqueKey<MemberVotedAnswerRecord> MEMBER_VOTED_ANSWER_PKEY = UniqueKeys0.MEMBER_VOTED_ANSWER_PKEY;
    public static final UniqueKey<MemberVotedCommentRecord> MEMBER_VOTED_COMMENT_PKEY = UniqueKeys0.MEMBER_VOTED_COMMENT_PKEY;
    public static final UniqueKey<MemberVotedQuestionRecord> MEMBER_VOTED_QUESTION_PKEY = UniqueKeys0.MEMBER_VOTED_QUESTION_PKEY;
    public static final UniqueKey<NotificationRecord> NOTIFICATION_PKEY = UniqueKeys0.NOTIFICATION_PKEY;
    public static final UniqueKey<PhotoRecord> PHOTO_PKEY = UniqueKeys0.PHOTO_PKEY;
    public static final UniqueKey<QuestionRecord> QUESTION_PKEY = UniqueKeys0.QUESTION_PKEY;
    public static final UniqueKey<QuestionMemberQuestionClosingRemarkRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY = UniqueKeys0.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY;
    public static final UniqueKey<TagRecord> TAG_PKEY = UniqueKeys0.TAG_PKEY;
    public static final UniqueKey<TagRecord> AK_TAG_NAME = UniqueKeys0.AK_TAG_NAME;

    public static final ForeignKey<AnswerRecord, AccountRecord> ANSWER__FK_ANSWER_AUTHOR_ID = ForeignKeys0.ANSWER__FK_ANSWER_AUTHOR_ID;
    public static final ForeignKey<AnswerRecord, QuestionRecord> ANSWER__FK_ANSWER_QUESTION_ID = ForeignKeys0.ANSWER__FK_ANSWER_QUESTION_ID;
    public static final ForeignKey<BountyRecord, AccountRecord> BOUNTY__FK_CREATOR_ID = ForeignKeys0.BOUNTY__FK_CREATOR_ID;
    public static final ForeignKey<CommentRecord, AccountRecord> COMMENT__FK_COMMENT_AUTHOR_ID = ForeignKeys0.COMMENT__FK_COMMENT_AUTHOR_ID;
    public static final ForeignKey<CommentRecord, AnswerRecord> COMMENT__FK_COMMENT_ANSWER_ID = ForeignKeys0.COMMENT__FK_COMMENT_ANSWER_ID;
    public static final ForeignKey<CommentRecord, QuestionRecord> COMMENT__FK_COMMENT_QUESTION_ID = ForeignKeys0.COMMENT__FK_COMMENT_QUESTION_ID;
    public static final ForeignKey<MemberBadgeQuestionRecord, AccountRecord> MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_ACCOUNT_ID = ForeignKeys0.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_ACCOUNT_ID;
    public static final ForeignKey<MemberBadgeQuestionRecord, QuestionRecord> MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_QUESTION_ID = ForeignKeys0.MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_QUESTION_ID;
    public static final ForeignKey<MemberNotificationRecord, AccountRecord> MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_ACCOUNT_ID = ForeignKeys0.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_ACCOUNT_ID;
    public static final ForeignKey<MemberNotificationRecord, NotificationRecord> MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_NOTIFICATION_ID = ForeignKeys0.MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_NOTIFICATION_ID;
    public static final ForeignKey<MemberVotedAnswerRecord, AccountRecord> MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ACCOUNT_ID = ForeignKeys0.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ACCOUNT_ID;
    public static final ForeignKey<MemberVotedAnswerRecord, AnswerRecord> MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ANSWER_ID = ForeignKeys0.MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ANSWER_ID;
    public static final ForeignKey<MemberVotedCommentRecord, AccountRecord> MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_ACCOUNT_ID = ForeignKeys0.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_ACCOUNT_ID;
    public static final ForeignKey<MemberVotedCommentRecord, CommentRecord> MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_COMMENT_ID = ForeignKeys0.MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_COMMENT_ID;
    public static final ForeignKey<MemberVotedQuestionRecord, AccountRecord> MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_ACCOUNT_ID = ForeignKeys0.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_ACCOUNT_ID;
    public static final ForeignKey<MemberVotedQuestionRecord, QuestionRecord> MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_QUESTION_ID = ForeignKeys0.MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_QUESTION_ID;
    public static final ForeignKey<PhotoRecord, QuestionRecord> PHOTO__FK_PHOTO_QUESTION_ID = ForeignKeys0.PHOTO__FK_PHOTO_QUESTION_ID;
    public static final ForeignKey<PhotoRecord, AnswerRecord> PHOTO__FK_PHOTO_ANSWER_ID = ForeignKeys0.PHOTO__FK_PHOTO_ANSWER_ID;
    public static final ForeignKey<QuestionRecord, AccountRecord> QUESTION__FK_AUTHOR_ID = ForeignKeys0.QUESTION__FK_AUTHOR_ID;
    public static final ForeignKey<QuestionRecord, BountyRecord> QUESTION__FK_BOUNTY_ID = ForeignKeys0.QUESTION__FK_BOUNTY_ID;
    public static final ForeignKey<QuestionMemberQuestionClosingRemarkRecord, QuestionRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_QUESTION_ID = ForeignKeys0.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_QUESTION_ID;
    public static final ForeignKey<QuestionMemberQuestionClosingRemarkRecord, AccountRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_ACCOUNT_ID = ForeignKeys0.QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_ACCOUNT_ID;
    public static final ForeignKey<TagQuestionRecord, TagRecord> TAG_QUESTION__FK_TAG_QUESTION_TAG_ID = ForeignKeys0.TAG_QUESTION__FK_TAG_QUESTION_TAG_ID;
    public static final ForeignKey<TagQuestionRecord, QuestionRecord> TAG_QUESTION__FK_TAG_QUESTION_QUESTION_ID = ForeignKeys0.TAG_QUESTION__FK_TAG_QUESTION_QUESTION_ID;

    private static class Identities0 {
        public static Identity<AccountRecord, Long> IDENTITY_ACCOUNT = Internal.createIdentity(AccountTable.ACCOUNT_TABLE, AccountTable.ID);
        public static Identity<AnswerRecord, Long> IDENTITY_ANSWER = Internal.createIdentity(AnswerTable.ANSWER_TABLE, AnswerTable.ID);
        public static Identity<BountyRecord, Long> IDENTITY_BOUNTY = Internal.createIdentity(BountyTable.BOUNTY_TABLE, BountyTable.ID);
        public static Identity<CommentRecord, Long> IDENTITY_COMMENT = Internal.createIdentity(CommentTable.COMMENT_TABLE, CommentTable.ID);
        public static Identity<NotificationRecord, Long> IDENTITY_NOTIFICATION = Internal.createIdentity(NotificationTable.NOTIFICATION_TABLE, NotificationTable.ID);
        public static Identity<PhotoRecord, Long> IDENTITY_PHOTO = Internal.createIdentity(PhotoTable.PHOTO_TABLE, PhotoTable.ID);
        public static Identity<QuestionRecord, Long> IDENTITY_QUESTION = Internal.createIdentity(QuestionTable.QUESTION_TABLE, QuestionTable.ID);
        public static Identity<TagRecord, Long> IDENTITY_TAG = Internal.createIdentity(TagTable.TAG_TABLE, TagTable.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountRecord> ACCOUNT_PKEY = Internal.createUniqueKey(AccountTable.ACCOUNT_TABLE, "account_pkey", AccountTable.ID);
        public static final UniqueKey<AccountRecord> AK_ACCOUNT_EMAIL = Internal.createUniqueKey(AccountTable.ACCOUNT_TABLE, "ak_account_email", AccountTable.EMAIL);
        public static final UniqueKey<AnswerRecord> ANSWER_PKEY = Internal.createUniqueKey(AnswerTable.ANSWER_TABLE, "answer_pkey", AnswerTable.ID);
        public static final UniqueKey<BountyRecord> BOUNTY_PKEY = Internal.createUniqueKey(BountyTable.BOUNTY_TABLE, "bounty_pkey", BountyTable.ID);
        public static final UniqueKey<CommentRecord> COMMENT_PKEY = Internal.createUniqueKey(CommentTable.COMMENT_TABLE, "comment_pkey", CommentTable.ID);
        public static final UniqueKey<MemberBadgeQuestionRecord> MEMBER_BADGE_QUESTION_PKEY = Internal.createUniqueKey(MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE, "member_badge_question_pkey", MemberBadgeQuestionTable.ACCOUNT_ID, MemberBadgeQuestionTable.BADGE, MemberBadgeQuestionTable.QUESTION_ID);
        public static final UniqueKey<MemberNotificationRecord> MEMBER_NOTIFICATION_PKEY = Internal.createUniqueKey(MemberNotificationTable.MEMBER_NOTIFICATION_TABLE, "member_notification_pkey", MemberNotificationTable.ACCOUNT_ID, MemberNotificationTable.NOTIFICATION_ID);
        public static final UniqueKey<MemberVotedAnswerRecord> MEMBER_VOTED_ANSWER_PKEY = Internal.createUniqueKey(MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE, "member_voted_answer_pkey", MemberVotedAnswerTable.ACCOUNT_ID, MemberVotedAnswerTable.ANSWER_ID);
        public static final UniqueKey<MemberVotedCommentRecord> MEMBER_VOTED_COMMENT_PKEY = Internal.createUniqueKey(MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE, "member_voted_comment_pkey", MemberVotedCommentTable.ACCOUNT_ID, MemberVotedCommentTable.COMMENT_ID);
        public static final UniqueKey<MemberVotedQuestionRecord> MEMBER_VOTED_QUESTION_PKEY = Internal.createUniqueKey(MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE, "member_voted_question_pkey", MemberVotedQuestionTable.ACCOUNT_ID, MemberVotedQuestionTable.QUESTION_ID);
        public static final UniqueKey<NotificationRecord> NOTIFICATION_PKEY = Internal.createUniqueKey(NotificationTable.NOTIFICATION_TABLE, "notification_pkey", NotificationTable.ID);
        public static final UniqueKey<PhotoRecord> PHOTO_PKEY = Internal.createUniqueKey(PhotoTable.PHOTO_TABLE, "photo_pkey", PhotoTable.ID);
        public static final UniqueKey<QuestionRecord> QUESTION_PKEY = Internal.createUniqueKey(QuestionTable.QUESTION_TABLE, "question_pkey", QuestionTable.ID);
        public static final UniqueKey<QuestionMemberQuestionClosingRemarkRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK_PKEY = Internal.createUniqueKey(QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "question_member_question_closing_remark_pkey", QuestionMemberQuestionClosingRemarkTable.QUESTION_ID, QuestionMemberQuestionClosingRemarkTable.ACCOUNT_ID, QuestionMemberQuestionClosingRemarkTable.CLOSING_REMARK);
        public static final UniqueKey<TagRecord> TAG_PKEY = Internal.createUniqueKey(TagTable.TAG_TABLE, "tag_pkey", TagTable.ID);
        public static final UniqueKey<TagRecord> AK_TAG_NAME = Internal.createUniqueKey(TagTable.TAG_TABLE, "ak_tag_name", TagTable.NAME);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AnswerRecord, AccountRecord> ANSWER__FK_ANSWER_AUTHOR_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, AnswerTable.ANSWER_TABLE, "answer__fk_answer_author_id", AnswerTable.AUTHOR_ID);
        public static final ForeignKey<AnswerRecord, QuestionRecord> ANSWER__FK_ANSWER_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, AnswerTable.ANSWER_TABLE, "answer__fk_answer_question_id", AnswerTable.QUESTION_ID);
        public static final ForeignKey<BountyRecord, AccountRecord> BOUNTY__FK_CREATOR_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, BountyTable.BOUNTY_TABLE, "bounty__fk_creator_id", BountyTable.CREATOR_ID);
        public static final ForeignKey<CommentRecord, AccountRecord> COMMENT__FK_COMMENT_AUTHOR_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, CommentTable.COMMENT_TABLE, "comment__fk_comment_author_id", CommentTable.AUTHOR_ID);
        public static final ForeignKey<CommentRecord, AnswerRecord> COMMENT__FK_COMMENT_ANSWER_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ANSWER_PKEY, CommentTable.COMMENT_TABLE, "comment__fk_comment_answer_id", CommentTable.ANSWER_ID);
        public static final ForeignKey<CommentRecord, QuestionRecord> COMMENT__FK_COMMENT_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, CommentTable.COMMENT_TABLE, "comment__fk_comment_question_id", CommentTable.QUESTION_ID);
        public static final ForeignKey<MemberBadgeQuestionRecord, AccountRecord> MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE, "member_badge_question__fk_account_badge_question_account_id", MemberBadgeQuestionTable.ACCOUNT_ID);
        public static final ForeignKey<MemberBadgeQuestionRecord, QuestionRecord> MEMBER_BADGE_QUESTION__FK_ACCOUNT_BADGE_QUESTION_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE, "member_badge_question__fk_account_badge_question_question_id", MemberBadgeQuestionTable.QUESTION_ID);
        public static final ForeignKey<MemberNotificationRecord, AccountRecord> MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, MemberNotificationTable.MEMBER_NOTIFICATION_TABLE, "member_notification__fk_account_notification_account_id", MemberNotificationTable.ACCOUNT_ID);
        public static final ForeignKey<MemberNotificationRecord, NotificationRecord> MEMBER_NOTIFICATION__FK_ACCOUNT_NOTIFICATION_NOTIFICATION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.NOTIFICATION_PKEY, MemberNotificationTable.MEMBER_NOTIFICATION_TABLE, "member_notification__fk_account_notification_notification_id", MemberNotificationTable.NOTIFICATION_ID);
        public static final ForeignKey<MemberVotedAnswerRecord, AccountRecord> MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE, "member_voted_answer__fk_account_voted_answer_account_id", MemberVotedAnswerTable.ACCOUNT_ID);
        public static final ForeignKey<MemberVotedAnswerRecord, AnswerRecord> MEMBER_VOTED_ANSWER__FK_ACCOUNT_VOTED_ANSWER_ANSWER_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ANSWER_PKEY, MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE, "member_voted_answer__fk_account_voted_answer_answer_id", MemberVotedAnswerTable.ANSWER_ID);
        public static final ForeignKey<MemberVotedCommentRecord, AccountRecord> MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE, "member_voted_comment__fk_account_voted_comment_account_id", MemberVotedCommentTable.ACCOUNT_ID);
        public static final ForeignKey<MemberVotedCommentRecord, CommentRecord> MEMBER_VOTED_COMMENT__FK_ACCOUNT_VOTED_COMMENT_COMMENT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.COMMENT_PKEY, MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE, "member_voted_comment__fk_account_voted_comment_comment_id", MemberVotedCommentTable.COMMENT_ID);
        public static final ForeignKey<MemberVotedQuestionRecord, AccountRecord> MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE, "member_voted_question__fk_account_voted_question_account_id", MemberVotedQuestionTable.ACCOUNT_ID);
        public static final ForeignKey<MemberVotedQuestionRecord, QuestionRecord> MEMBER_VOTED_QUESTION__FK_ACCOUNT_VOTED_QUESTION_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE, "member_voted_question__fk_account_voted_question_question_id", MemberVotedQuestionTable.QUESTION_ID);
        public static final ForeignKey<PhotoRecord, QuestionRecord> PHOTO__FK_PHOTO_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, PhotoTable.PHOTO_TABLE, "photo__fk_photo_question_id", PhotoTable.QUESTION_ID);
        public static final ForeignKey<PhotoRecord, AnswerRecord> PHOTO__FK_PHOTO_ANSWER_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ANSWER_PKEY, PhotoTable.PHOTO_TABLE, "photo__fk_photo_answer_id", PhotoTable.ANSWER_ID);
        public static final ForeignKey<QuestionRecord, AccountRecord> QUESTION__FK_AUTHOR_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, QuestionTable.QUESTION_TABLE, "question__fk_author_id", QuestionTable.AUTHOR_ID);
        public static final ForeignKey<QuestionRecord, BountyRecord> QUESTION__FK_BOUNTY_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.BOUNTY_PKEY, QuestionTable.QUESTION_TABLE, "question__fk_bounty_id", QuestionTable.BOUNTY_ID);
        public static final ForeignKey<QuestionMemberQuestionClosingRemarkRecord, QuestionRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "question_member_question_closing_remark__fk_question_member_question_closing_remark_question_id", QuestionMemberQuestionClosingRemarkTable.QUESTION_ID);
        public static final ForeignKey<QuestionMemberQuestionClosingRemarkRecord, AccountRecord> QUESTION_MEMBER_QUESTION_CLOSING_REMARK__FK_QUESTION_MEMBER_QUESTION_CLOSING_REMARK_ACCOUNT_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.ACCOUNT_PKEY, QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, "question_member_question_closing_remark__fk_question_member_question_closing_remark_account_id", QuestionMemberQuestionClosingRemarkTable.ACCOUNT_ID);
        public static final ForeignKey<TagQuestionRecord, TagRecord> TAG_QUESTION__FK_TAG_QUESTION_TAG_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.TAG_PKEY, TagQuestionTable.TAG_QUESTION_TABLE, "tag_question__fk_tag_question_tag_id", TagQuestionTable.TAG_ID);
        public static final ForeignKey<TagQuestionRecord, QuestionRecord> TAG_QUESTION__FK_TAG_QUESTION_QUESTION_ID = Internal.createForeignKey(com.ra.course.com.stackoverflow.entity.jooq.Keys.QUESTION_PKEY, TagQuestionTable.TAG_QUESTION_TABLE, "tag_question__fk_tag_question_question_id", TagQuestionTable.QUESTION_ID);
    }
}
