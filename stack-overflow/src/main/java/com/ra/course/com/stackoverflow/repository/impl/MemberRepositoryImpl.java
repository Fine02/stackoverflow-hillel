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
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.AccountTable.ACCOUNT_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedAnswerTable.MEMBER_VOTED_ANSWER_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedCommentTable.MEMBER_VOTED_COMMENT_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.MemberVotedQuestionTable.MEMBER_VOTED_QUESTION_TABLE;

@Repository
@AllArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final DSLContext dslContext;
    private final QuestionRepository questionRepo;
    private final AnswerRepository answerRepo;
    private final CommentRepository commentRepo;

    @Override
    public Member save(@NonNull final Member member) {
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
    public Optional<Member> findById(final long id) {

        final var accountRecord = dslContext.fetchOne(ACCOUNT_TABLE, ACCOUNT_TABLE.ID.eq(id));

        if (accountRecord == null) {
            return Optional.empty();
        }

        final Member returningMember = mapperMember(accountRecord);
        return Optional.of(returningMember);
    }

    @Override
    public void delete(@NonNull final Member member) {
        final var memberFromDb = findById(member.getAccount().getId());

        if (memberFromDb.isPresent()) {
            memberFromDb.get().getAccount().setStatus(AccountStatus.ARCHIVED);
            update(memberFromDb.get());
        }
    }

    @Override
    public void update(@NonNull final Member member) {
        dslContext.update(ACCOUNT_TABLE)
                .set(ACCOUNT_TABLE.PASSWORD, member.getAccount().getPassword())
                .set(ACCOUNT_TABLE.ACCOUNT_STATUS, AccountStatusType.valueOf(member.getAccount().getStatus().toString().toLowerCase(Locale.US)))
                .set(ACCOUNT_TABLE.NAME, member.getAccount().getName())
                .set(ACCOUNT_TABLE.REPUTATION, member.getAccount().getReputation())
                .where(ACCOUNT_TABLE.ID.eq(member.getAccount().getId()))
                .execute();
    }

    @Override
    public List<Member> findByMemberName(@NonNull final String name) {
        return dslContext.selectFrom(ACCOUNT_TABLE)
                .where(ACCOUNT_TABLE.NAME.contains(name))
                .fetchStream()
                .map(this::mapperMember)
                .collect(Collectors.toList());
    }


    private Member mapperMember(final AccountRecord accountRecord) {
        final var account = Account.builder()
                .id(accountRecord.getId())
                .password(accountRecord.getPassword())
                .status(AccountStatus.valueOf(accountRecord.getAccountStatus().toString().toUpperCase(Locale.US)))
                .name(accountRecord.getName())
                .email(accountRecord.getEmail())
                .reputation(accountRecord.getReputation())
                .build();

        return  Member.builder()
                .account(account)
                .questions(questionRepo.findByMemberId(account.getId()))
                .answers(answerRepo.findByMemberId(account.getId()))
                .comments(commentRepo.findByMemberId(account.getId()))
                .upVotedQuestionsId(getUpVotedQuestionsId(account.getId()))
                .upVotedAnswersId(getUpVotedAnswersId(account.getId()))
                .upVotedCommentsId(getUpVotedCommentsId(account.getId()))
                .downVotedQuestionsId(getDownVotedQuestionsId(account.getId()))
                .downVotedAnswersId(getDownVotedAnswersId(account.getId()))
                .downVotedCommentsId(getDownVotedCommentsId(account.getId()))
                .build();
    }


    private List<Long> getUpVotedQuestionsId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_QUESTION_TABLE)
                .where(MEMBER_VOTED_QUESTION_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(MemberVotedQuestionRecord::getUpvoted)
                .map(MemberVotedQuestionRecord::getQuestionId)
                .collect(Collectors.toList());
    }

    private List<Long> getDownVotedQuestionsId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_QUESTION_TABLE)
                .where(MEMBER_VOTED_QUESTION_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(r -> !r.getUpvoted())
                .map(MemberVotedQuestionRecord::getQuestionId)
                .collect(Collectors.toList());
    }

    private List<Long> getUpVotedAnswersId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_ANSWER_TABLE)
                .where(MEMBER_VOTED_ANSWER_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(MemberVotedAnswerRecord::getUpvoted)
                .map(MemberVotedAnswerRecord::getAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getDownVotedAnswersId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_ANSWER_TABLE)
                .where(MEMBER_VOTED_ANSWER_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(r -> !r.getUpvoted())
                .map(MemberVotedAnswerRecord::getAccountId)
                .collect(Collectors.toList());
    }

    private List<Long> getUpVotedCommentsId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_COMMENT_TABLE)
                .where(MEMBER_VOTED_COMMENT_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(MemberVotedCommentRecord::getUpvoted)
                .map(MemberVotedCommentRecord::getCommentId)
                .collect(Collectors.toList());
    }

    private List<Long> getDownVotedCommentsId(final long id) {
        return dslContext.selectFrom(MEMBER_VOTED_COMMENT_TABLE)
                .where(MEMBER_VOTED_COMMENT_TABLE.ACCOUNT_ID.eq(id))
                .fetch()
                .stream()
                .filter(r -> !r.getUpvoted())
                .map(MemberVotedCommentRecord::getCommentId)
                .collect(Collectors.toList());
    }
}
