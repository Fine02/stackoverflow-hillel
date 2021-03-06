package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.entity.jooq.enums.AccountStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AccountRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedAnswerRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedCommentRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.MemberVotedQuestionRecord;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.AccountTable.ACCOUNT_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedAnswerTable.*;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedCommentTable.*;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedQuestionTable.*;

@Repository
@AllArgsConstructor
public class MemberRepositoryJooqImpl implements MemberRepository {

    private final DSLContext dslContext;
    private final QuestionRepository questionRepo;
    private final AnswerRepository answerRepo;
    private final CommentRepository commentRepo;

    @Override
    public Member save(final Member member) {
        final var accountRecord = dslContext.insertInto(ACCOUNT_TABLE, ACCOUNT_TABLE.PASSWORD, ACCOUNT_TABLE.ACCOUNT_STATUS, ACCOUNT_TABLE.NAME,
                ACCOUNT_TABLE.EMAIL, ACCOUNT_TABLE.REPUTATION)
                .values(member.getAccount().getPassword(),
                        AccountStatusType.valueOf(member.getAccount().getStatus().toString().toLowerCase(Locale.US)), member.getAccount().getName(),
                        member.getAccount().getEmail(), member.getAccount().getReputation())
                .returning()
                .fetchOne();
        return mapperMember(accountRecord);
    }

    @Override
    public Optional<Member> findById(final Long id) {

        final var accountRecord = dslContext.fetchOne(ACCOUNT_TABLE, ACCOUNT_TABLE.ID.eq(id));

        final Member returningMember = mapperMember(accountRecord);
        return Optional.ofNullable(returningMember);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        final var accountRecord = dslContext.fetchOne(ACCOUNT_TABLE, ACCOUNT_TABLE.EMAIL.eq(email));

        final Member returningMember = mapperMember(accountRecord);
        return Optional.ofNullable(returningMember);
    }

    @Override
    public void delete(final Member member) {
        member.getAccount().setStatus(AccountStatus.ARCHIVED);
        update(member);

    }

    @Override
    public void update(final Member member) {
        dslContext.update(ACCOUNT_TABLE)
                .set(ACCOUNT_TABLE.PASSWORD, member.getAccount().getPassword())
                .set(ACCOUNT_TABLE.ACCOUNT_STATUS, AccountStatusType.valueOf(member.getAccount().getStatus().toString().toLowerCase(Locale.US)))
                .set(ACCOUNT_TABLE.NAME, member.getAccount().getName())
                .set(ACCOUNT_TABLE.REPUTATION, member.getAccount().getReputation())
                .where(ACCOUNT_TABLE.ID.eq(member.getAccount().getId()))
                .execute();
        updateVotedQuestionId(member, true); // upvote question
        updateVotedQuestionId(member, false); // downvote question
        updateVotedAnswerId(member, true); // upvote answer
        updateVotedAnswerId(member, false); // downvote answer
        updateVotedCommentId(member, true); // upvote comment
        updateVotedCommentId(member, false); // downvote comment

    }

    private void updateVotedQuestionId(final Member member, final Boolean bool) {
        final var questionsId = bool ? member.getUpVotedQuestionsId()
                : member.getDownVotedQuestionsId();
        questionsId.forEach(
                id -> dslContext.insertInto(MEMBER_VOTED_QUESTION_TABLE, MEMBER_VOTED_QUESTION_TABLE.ACCOUNT_ID, MEMBER_VOTED_QUESTION_TABLE.QUESTION_ID, MEMBER_VOTED_QUESTION_TABLE.UPVOTED)
                        .values(member.getAccount().getId(), id, bool)
                        .onDuplicateKeyUpdate()
                        .set(MEMBER_VOTED_QUESTION_TABLE.UPVOTED, bool)
                        .execute()
        );
    }

    private void updateVotedAnswerId(final Member member, final Boolean bool) {
        final var answersId = bool ? member.getUpVotedAnswersId()
                : member.getDownVotedAnswersId();
        answersId.forEach(
                id -> dslContext.insertInto(MEMBER_VOTED_ANSWER_TABLE, MEMBER_VOTED_ANSWER_TABLE.ACCOUNT_ID, MEMBER_VOTED_ANSWER_TABLE.ANSWER_ID, MEMBER_VOTED_ANSWER_TABLE.UPVOTED)
                        .values(member.getAccount().getId(), id, bool)
                        .onDuplicateKeyUpdate()
                        .set(MEMBER_VOTED_ANSWER_TABLE.UPVOTED, bool)
                        .execute()
        );
    }

    private void updateVotedCommentId(final Member member, final Boolean bool) {
        final var commentsId = bool ? member.getUpVotedCommentsId()
                : member.getDownVotedCommentsId();
        commentsId.forEach(
                id -> dslContext.insertInto(MEMBER_VOTED_COMMENT_TABLE, MEMBER_VOTED_COMMENT_TABLE.ACCOUNT_ID, MEMBER_VOTED_COMMENT_TABLE.COMMENT_ID, MEMBER_VOTED_COMMENT_TABLE.UPVOTED)
                        .values(member.getAccount().getId(), id, bool)
                        .onDuplicateKeyUpdate()
                        .set(MEMBER_VOTED_COMMENT_TABLE.UPVOTED, bool)
                        .execute()
        );
    }

    @Override
    public List<Member> findByMemberName(final String name) {
        return dslContext.selectFrom(ACCOUNT_TABLE)
                .where(ACCOUNT_TABLE.NAME.containsIgnoreCase(name))
                .fetchStream()
                .map(this::mapperMember)
                .collect(Collectors.toList());
    }


    private Member mapperMember(final AccountRecord accountRecord) {
        if (Objects.isNull(accountRecord)) {
            return null;
        }

        final var account = new Account();
        account.setId(accountRecord.getId());
        account.setPassword(accountRecord.getPassword());
        account.setStatus(AccountStatus.valueOf(accountRecord.getAccountStatus().toString().toUpperCase(Locale.US)));
        account.setName(accountRecord.getName());
        account.setEmail(accountRecord.getEmail());
        account.setReputation(accountRecord.getReputation());

        final var member = new Member();
        member.setAccount(account);
        member.setQuestions(questionRepo.findByMemberId(account.getId()));
        member.setAnswers(answerRepo.findByMemberId(account.getId()));
        member.setComments(commentRepo.findByMemberId(account.getId()));
        member.setUpVotedQuestionsId(getVotedQuestionsId(account.getId(), true));
        member.setUpVotedAnswersId(getVotedAnswersId(account.getId(), true));
        member.setUpVotedCommentsId(getVotedCommentsId(account.getId(), true));
        member.setDownVotedQuestionsId(getVotedQuestionsId(account.getId(), false));
        member.setDownVotedAnswersId(getVotedAnswersId(account.getId(), false));
        member.setDownVotedCommentsId(getVotedCommentsId(account.getId(), false));

        return member;
    }


    private List<Long> getVotedQuestionsId(final Long id, final Boolean bool) {
        final Predicate<MemberVotedQuestionRecord> predicate = bool
                ? MemberVotedQuestionRecord::getUpvoted
                : r -> !r.getUpvoted();
        return dslContext.selectFrom(MEMBER_VOTED_QUESTION_TABLE)
                .where(MEMBER_VOTED_QUESTION_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(predicate)
                .map(MemberVotedQuestionRecord::getQuestionId)
                .collect(Collectors.toList());
    }

    private List<Long> getVotedAnswersId(final Long id, final Boolean bool) {
        final Predicate<MemberVotedAnswerRecord> predicate = bool
                ? MemberVotedAnswerRecord::getUpvoted
                : r -> !r.getUpvoted();
        return dslContext.selectFrom(MEMBER_VOTED_ANSWER_TABLE)
                .where(MEMBER_VOTED_ANSWER_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(predicate)
                .map(MemberVotedAnswerRecord::getAnswerId)
                .collect(Collectors.toList());
    }


    private List<Long> getVotedCommentsId(final Long id, final Boolean bool) {
        final Predicate<MemberVotedCommentRecord> predicate = bool
                ? MemberVotedCommentRecord::getUpvoted
                : r -> !r.getUpvoted();
        return dslContext.selectFrom(MEMBER_VOTED_COMMENT_TABLE)
                .where(MEMBER_VOTED_COMMENT_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(predicate)
                .map(MemberVotedCommentRecord::getCommentId)
                .collect(Collectors.toList());
    }

}
