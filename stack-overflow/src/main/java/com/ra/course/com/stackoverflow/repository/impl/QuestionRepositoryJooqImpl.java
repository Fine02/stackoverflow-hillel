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
public class QuestionRepositoryJooqImpl implements QuestionRepository {

    private final DSLContext dslContext;
    private final BountyRepository bountyRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    @Override
    public Question save(final Question question) {

        final var questionRecord = dslContext.insertInto(QUESTION_TABLE, QUESTION_TABLE.TITLE, QUESTION_TABLE.DESCRIPTION,
                QUESTION_TABLE.VIEW_COUNT, QUESTION_TABLE.VOTE_COUNT, QUESTION_TABLE.CREATION_TIME, QUESTION_TABLE.UPDATE_TIME,
                QUESTION_TABLE.STATUS, QUESTION_TABLE.CLOSING_REMARK, QUESTION_TABLE.AUTHOR_ID)
                .values(question.getTitle(), question.getText(), question.getViewCount(), question.getVoteCount(),
                        Timestamp.valueOf(question.getCreationTime()), Timestamp.valueOf(question.getUpdateTime()),
                        QuestionStatusType.valueOf(question.getStatus().toString().toLowerCase(Locale.US)), QuestionClosingRemarkType.valueOf(question.getClosingRemark().toString().toLowerCase(Locale.US)),
                        question.getAuthor())
                .returning()
                .fetchOne();

        return mapperQuestion(questionRecord);
    }

    @Override
    public Optional<Question> findById( final Long id) {
        final var questionRecord = dslContext.fetchOne(QUESTION_TABLE, QUESTION_TABLE.ID.eq(id));

        final Question returningQuestion = mapperQuestion(questionRecord);
        return Optional.ofNullable(returningQuestion);
    }

    @Override
    public void delete( final Question question) {
        dslContext.delete(QUESTION_TABLE)
                .where(QUESTION_TABLE.ID.eq(question.getId()))
                .execute();
    }

    @Override
    public void update( final Question question) {
        final var bountyId = question.getBounty() != null ? question.getBounty().getId() : null;

        dslContext.update(QUESTION_TABLE)
                .set(QUESTION_TABLE.TITLE, question.getTitle())
                .set(QUESTION_TABLE.DESCRIPTION, question.getText())
                .set(QUESTION_TABLE.VIEW_COUNT, question.getViewCount())
                .set(QUESTION_TABLE.VOTE_COUNT, question.getVoteCount())
                .set(QUESTION_TABLE.UPDATE_TIME, Timestamp.valueOf(LocalDateTime.now()))
                .set(QUESTION_TABLE.STATUS, QuestionStatusType.valueOf(question.getStatus().toString().toLowerCase(Locale.US)))
                .set(QUESTION_TABLE.CLOSING_REMARK, QuestionClosingRemarkType.valueOf(question.getClosingRemark().toString().toLowerCase(Locale.US)))
                .set(QUESTION_TABLE.BOUNTY_ID, bountyId)
                .where(QUESTION_TABLE.ID.eq(question.getId()))
                .execute();
    }

    @Override
    public List<Question> findByMemberId( final Long id) {
        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.AUTHOR_ID.eq(id))
                .fetch()
                .stream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findByTag( final Tag tag) {
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
    public List<Question> findByTitle( final String searchPhrase) {
        return dslContext.selectFrom(QUESTION_TABLE)
                .where(QUESTION_TABLE.TITLE.containsIgnoreCase(searchPhrase))
                .fetch()
                .stream()
                .map(this::mapperQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> findByTitleAndTag( final String searchPhrase, final Tag tag) {
        return this.findByTitle(searchPhrase).stream()
                .filter(q -> q.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    @Override
    public void addTagToQuestion(final Tag tag, final Question question) {
        dslContext.insertInto(TAG_QUESTION_TABLE, TAG_QUESTION_TABLE.TAG_ID, TAG_QUESTION_TABLE.QUESTION_ID)
                .values(tag.getId(), question.getId())
                .execute();
    }


    //convert QuestionRecord.class into Question.class
    private Question mapperQuestion(final QuestionRecord questionRecord) {
        if (Objects.isNull(questionRecord)) {
            return null;
        }
        final var question = new Question();
            question.setId(questionRecord.getId());
            question.setTitle(questionRecord.getTitle());
            question.setText(questionRecord.getDescription());
            question.setViewCount(questionRecord.getViewCount());
            question.setVoteCount(questionRecord.getVoteCount());
            question.setCreationTime(questionRecord.getCreationTime().toLocalDateTime());
            question.setUpdateTime(questionRecord.getUpdateTime().toLocalDateTime());
            question.setStatus(QuestionStatus.valueOf(questionRecord.getStatus().toString().toUpperCase(Locale.US)));
            question.setClosingRemark(QuestionClosingRemark.valueOf(questionRecord.getClosingRemark().toString().toUpperCase(Locale.US)));
            question.setAuthor(questionRecord.getAuthorId());
            question.setBounty(bountyRepository.findById(questionRecord.getBountyId()).orElse(null));
            question.setComments(commentRepository.findByQuestionId(questionRecord.getId()));
            question.setAnswers(answerRepository.findByQuestionId(questionRecord.getId()));
            question.setPhotos(photoRepository.findByQuestionId(questionRecord.getId()));
            question.setTags(tagRepository.findByQuestionId(questionRecord.getId()));
            question.setMembersIdsWhoVotedQuestionToClose(getMapIdMembersClosingRemark(questionRecord));

        return question;
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
