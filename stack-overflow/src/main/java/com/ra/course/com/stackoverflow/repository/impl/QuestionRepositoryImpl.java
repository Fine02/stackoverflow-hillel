package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.QuestionRecord;
import com.ra.course.com.stackoverflow.repository.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.QUESTION_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.QuestionMemberQuestionClosingRemarkTable.QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.TagQuestionTable.TAG_QUESTION_TABLE;

@Repository
@AllArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final DSLContext dslContext;
    private final BountyRepository bountyRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    @Override
    public Question save(@NonNull final Question question) {
        final var questionRecord = dslContext.insertInto(QUESTION_TABLE, QUESTION_TABLE.TITLE, QUESTION_TABLE.DESCRIPTION,
                QUESTION_TABLE.VIEW_COUNT, QUESTION_TABLE.VOTE_COUNT, QUESTION_TABLE.CREATION_TIME, QUESTION_TABLE.UPDATE_TIME,
                QUESTION_TABLE.STATUS, QUESTION_TABLE.CLOSING_REMARK, QUESTION_TABLE.AUTHOR_ID, QUESTION_TABLE.BOUNTY_ID)
                .values(question.getTitle(), question.getDescription(), question.getViewCount(), question.getVoteCount(),
                        Timestamp.valueOf(question.getCreationTime()), Timestamp.valueOf(question.getUpdateTime()),
                        QuestionStatusType.valueOf(question.getStatus().toString().toLowerCase(Locale.US)), QuestionClosingRemarkType.valueOf(question.getClosingRemark().toString().toLowerCase(Locale.US)),
                        question.getAuthorId(), question.getBounty().get().getId())
                .returning()
                .fetchOne();

        return mapperQuestion(questionRecord);
    }

    @Override
    public Optional<Question> findById(@NonNull final Long id) {
        final var questionRecord = dslContext.fetchOne(QUESTION_TABLE, QUESTION_TABLE.ID.eq(id));

        if (questionRecord == null) {
            return Optional.empty();
        }

        final Question returningQuestion = mapperQuestion(questionRecord);
        return Optional.of(returningQuestion);
    }

    @Override
    public void delete(@NonNull final Question question) {
        dslContext.delete(QUESTION_TABLE)
                .where(QUESTION_TABLE.ID.eq(question.getId()))
                .execute();
    }

    @Override
    public void update(@NonNull final Question question) {
        dslContext.update(QUESTION_TABLE)
                .set(QUESTION_TABLE.TITLE, question.getTitle())
                .set(QUESTION_TABLE.DESCRIPTION, question.getDescription())
                .set(QUESTION_TABLE.VIEW_COUNT, question.getViewCount())
                .set(QUESTION_TABLE.VOTE_COUNT, question.getVoteCount())
                .set(QUESTION_TABLE.UPDATE_TIME, Timestamp.valueOf(LocalDateTime.now()))
                .set(QUESTION_TABLE.STATUS, QuestionStatusType.valueOf(question.getStatus().toString().toLowerCase(Locale.US)))
                .set(QUESTION_TABLE.CLOSING_REMARK, QuestionClosingRemarkType.valueOf(question.getClosingRemark().toString().toLowerCase(Locale.US)))
                .set(QUESTION_TABLE.BOUNTY_ID, question.getBounty().get().getId())
                .where(QUESTION_TABLE.ID.eq(question.getId()))
                .execute();
        final Iterator<Map.Entry<Long, QuestionClosingRemark>> iterator = question.getMembersIdsWhoVotedQuestionToClose().entrySet().iterator();
        while(iterator.hasNext()) {
            final Map.Entry<Long, QuestionClosingRemark> entry = iterator.next();
            dslContext.insertInto(QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE, QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE.QUESTION_ID, QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE.ACCOUNT_ID, QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE.CLOSING_REMARK)
                    .values(question.getId(), entry.getKey(),QuestionClosingRemarkType.valueOf(entry.getValue().toString().toLowerCase(Locale.US)))
                    .onDuplicateKeyUpdate()
                    .set(QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE.CLOSING_REMARK, QuestionClosingRemarkType.valueOf(entry.getValue().toString().toLowerCase(Locale.US)))
                    .execute();
        }
    }

    @Override
    public List<Question> findByMemberId(@NonNull final Long id) {
        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.AUTHOR_ID.eq(id))
                .fetch()
                .stream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findByTag(@NonNull final Tag tag) {
        return dslContext.select(TAG_QUESTION_TABLE.QUESTION_ID)
                .from(TAG_QUESTION_TABLE)
                .where(TAG_QUESTION_TABLE.TAG_ID.eq(tag.getId()))
                .fetch()
                .stream()
                .map(s -> s.getValue(TAG_QUESTION_TABLE.QUESTION_ID))
                .map(this::findById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findByTitle(@NonNull final String searchPhrase) {
        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.TITLE.contains(searchPhrase))
                .fetch()
                .stream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findByTitleAndTag(@NonNull final String searchPhrase, @NonNull final Tag tag) {
        return dslContext.select(TAG_QUESTION_TABLE.QUESTION_ID)
                .from(TAG_QUESTION_TABLE)
                .where(TAG_QUESTION_TABLE.TAG_ID.eq(tag.getId()))
                .fetch()
                .stream()
                .map(s -> s.getValue(TAG_QUESTION_TABLE.QUESTION_ID))
                .map(this::findById)
                .map(Optional::get)
                .filter(q -> q.getTitle().contains(searchPhrase))
                .collect(Collectors.toList());
    }


    //convert QuestionRecord.class into Question.class
    private Question mapperQuestion(final QuestionRecord questionRecord) {
        return Question.builder()
                .id(questionRecord.getId())
                .title(questionRecord.getTitle())
                .description(questionRecord.getDescription())
                .viewCount(questionRecord.getViewCount())
                .voteCount(questionRecord.getVoteCount())
                .creationTime(questionRecord.getCreationTime().toLocalDateTime())
                .updateTime(questionRecord.getUpdateTime().toLocalDateTime())
                .status(QuestionStatus.valueOf(questionRecord.getStatus().toString().toUpperCase(Locale.US)))
                .closingRemark(QuestionClosingRemark.valueOf(questionRecord.getClosingRemark().toString().toUpperCase(Locale.US)))
                .authorId(questionRecord.getAuthorId())
                .bounty(bountyRepository.findById(questionRecord.getId()))
                .commentList(commentRepository.findByQuestionId(questionRecord.getId()))
                .answerList(answerRepository.findByQuestionId(questionRecord.getId()))
                .photoList(photoRepository.findByQuestionId(questionRecord.getId()))
                .tagList(tagRepository.findByQuestionId(questionRecord.getId()))
                .membersIdsWhoVotedQuestionToClose(getMapIdMembersClosingRemark(questionRecord))
                .build();

    }

    //get information from QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE for VoteWithRemarkService.class
    private Map<Long, QuestionClosingRemark> getMapIdMembersClosingRemark(final QuestionRecord questionRecord) {
        return dslContext.selectFrom(QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE)
                .where(QUESTION_MEMBER_QUESTION_CLOSING_REMARK_TABLE.QUESTION_ID.eq(questionRecord.getId()))
                .fetch()
                .stream()
                .collect(Collectors.toMap(k -> k.getAccountId(), v -> QuestionClosingRemark.valueOf(v.getClosingRemark().toString().toUpperCase(Locale.US))));
    }
}
