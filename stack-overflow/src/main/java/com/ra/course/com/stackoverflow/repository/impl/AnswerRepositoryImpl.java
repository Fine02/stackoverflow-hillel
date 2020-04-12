package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.ANSWER_TABLE;

@Repository
@AllArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

    private final DSLContext dslContext;
    private final CommentRepositoryImpl commentRepository;
    private final PhotoRepositoryImpl photoRepository;

    @Override
    public Answer save(@NonNull final Answer answer) {
        final var answerRecord = dslContext.insertInto(ANSWER_TABLE, ANSWER_TABLE.ANSWER_TEXT, ANSWER_TABLE.ACCEPTED, ANSWER_TABLE.VOTE_COUNT,
                ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATION_DATE, ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID).
                values(answer.getAnswerText(), answer.isAccepted(), answer.getVoteCount(),
                answer.getFlagCount(), Timestamp.valueOf(answer.getCreationDate()), answer.getAuthorId(), answer.getQuestionId())
                .returning()
                .fetchOne();

        return intoAnswer(answerRecord);
    }

    @Override
    public Optional<Answer> findById(@NonNull final Long id) {
        final var answerRecord = dslContext.fetchOne(ANSWER_TABLE, ANSWER_TABLE.ID.eq(id));

        if (answerRecord == null) {
            return Optional.empty();
        }

        final Answer returningAnswer = intoAnswer(answerRecord);

        return Optional.of(returningAnswer);
    }

    @Override
    public void delete(@NonNull final Answer answer) {
        dslContext.delete(ANSWER_TABLE)
                .where(ANSWER_TABLE.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public void update(@NonNull final Answer answer) {
        dslContext.update(ANSWER_TABLE)
                .set(ANSWER_TABLE.ANSWER_TEXT, answer.getAnswerText())
                .set(ANSWER_TABLE.ACCEPTED, answer.isAccepted())
                .set(ANSWER_TABLE.VOTE_COUNT, answer.getVoteCount())
                .set(ANSWER_TABLE.FLAG_COUNT, answer.getFlagCount())
                .where(ANSWER_TABLE.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public List<Answer> findByQuestionId(@NonNull final Long id) {
        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.QUESTION_ID.eq(id))
                .fetch()
                .stream()
                .map(this::intoAnswer)
                .collect(Collectors.toList());
    }

    @Override
    public List<Answer> findByMemberId(@NonNull final Long id) {
        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.AUTHOR_ID.eq(id))
                .fetch()
                .stream()
                .map(this::intoAnswer)
                .collect(Collectors.toList());
    }

    //convert AnswerRecord.class into Answer.class
    private Answer intoAnswer(final AnswerRecord answerRecord) {
        return  Answer.builder()
                .id(answerRecord.getId())
                .answerText(answerRecord.getAnswerText())
                .accepted(answerRecord.getAccepted())
                .voteCount(answerRecord.getVoteCount())
                .flagCount(answerRecord.getFlagCount())
                .creationDate(answerRecord.getCreationDate().toLocalDateTime())
                .authorId(answerRecord.getAuthorId())
                .questionId(answerRecord.getQuestionId())
                .photos(photoRepository.findByAnswerId(answerRecord.getId()))
                .comments(commentRepository.findByAnswerId(answerRecord.getId()))
                .build();
    }
}
