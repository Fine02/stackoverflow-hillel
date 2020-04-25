package com.ra.course.com.stackoverflow.entity.jooq;

import com.ra.course.com.stackoverflow.entity.jooq.tables.*;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    public static final AccountTable ACCOUNT_TABLE = AccountTable.ACCOUNT_TABLE;
    public static final AnswerTable ANSWER_TABLE = AnswerTable.ANSWER_TABLE;
    public static final BountyTable BOUNTY = BountyTable.BOUNTY_TABLE;
    public static final CommentTable COMMENT_TABLE = CommentTable.COMMENT_TABLE;
    public static final MemberBadgeQuestionTable MEMBER_BADGE_QUESTION = MemberBadgeQuestionTable.MEMBER_BADGE_QUESTION_TABLE;
    public static final MemberNotificationTable MEMBER_NOTIFICATION = MemberNotificationTable.MEMBER_NOTIFICATION_TABLE;
    public static final MemberVotedAnswerTable MEMBER_VOTED_ANSWER = MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE;
    public static final MemberVotedCommentTable MEMBER_VOTED_COMMENT = MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE;
    public static final MemberVotedQuestionTable MEMBER_VOTED_QUESTION = MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE;
    public static final NotificationTable NOTIFICATION_TABLE = NotificationTable.NOTIFICATION_TABLE;
    public static final PhotoTable PHOTO_TABLE = PhotoTable.PHOTO_TABLE;
    public static final QuestionTable QUESTION_TABLE = QuestionTable.QUESTION_TABLE;
    public static final QuestionMemberQuestionClosingRemarkTable QUESTION_MEMBER_QUESTION_CLOSING_REMARK = QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE;
    public static final TagTable TAG_TABLE = TagTable.TAG_TABLE;
    public static final TagQuestionTable TAG_QUESTION = TagQuestionTable.TAG_QUESTION_TABLE;
}
