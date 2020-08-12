package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.CommentRepository;
import com.ra.course.com.stackoverflow.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.ANSWER_TABLE;

@Repository
@AllArgsConstructor
public class AnswerRepositoryJooqImpl implements AnswerRepository {

    private final DSLContext dslContext;
    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;

    @Override
    public Answer save(final Answer answer) {
        final var answerRecord = dslContext.insertInto(ANSWER_TABLE, ANSWER_TABLE.ANSWER_TEXT, ANSWER_TABLE.ACCEPTED, ANSWER_TABLE.VOTE_COUNT,
                ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATION_DATE, ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID).
                values(answer.getText(), answer.isAccepted(), answer.getVoteCount(),
                answer.getFlagCount(), Timestamp.valueOf(answer.getCreationTime()), answer.getAuthor(), answer.getQuestion())
                .returning()
                .fetchOne();

        return intoAnswer(answerRecord);
    }

    @Override
    public Optional<Answer> findById(final Long id) {
        final var answerRecord = dslContext.fetchOne(ANSWER_TABLE, ANSWER_TABLE.ID.eq(id));

        return Optional.ofNullable(intoAnswer(answerRecord));
    }

    @Override
    public void delete(final Answer answer) {
        dslContext.delete(ANSWER_TABLE)
                .where(ANSWER_TABLE.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public void update(final Answer answer) {
        dslContext.update(ANSWER_TABLE)
                .set(ANSWER_TABLE.ANSWER_TEXT, answer.getText())
                .set(ANSWER_TABLE.ACCEPTED, answer.isAccepted())
                .set(ANSWER_TABLE.VOTE_COUNT, answer.getVoteCount())
                .set(ANSWER_TABLE.FLAG_COUNT, answer.getFlagCount())
                .where(ANSWER_TABLE.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public List<Answer> findByQuestionId(final Long id) {
        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.QUESTION_ID.eq(id))
                .fetchStream()
                .map(this::intoAnswer)
                .collect(Collectors.toList());
    }

    @Override
    public List<Answer> findByMemberId(final Long id) {
        return dslContext.selectFrom(ANSWER_TABLE)
                .where(ANSWER_TABLE.AUTHOR_ID.eq(id))
                .fetchStream()
                .map(this::intoAnswer)
                .collect(Collectors.toList());
    }

    //convert AnswerRecord.class into Answer.class
    private Answer intoAnswer(final AnswerRecord answerRecord) {
        if (Objects.isNull(answerRecord)){
            return null;
        }
        final var answer = new Answer();
            answer.setId(answerRecord.getId());
            answer.setText(answerRecord.getAnswerText());
            answer.setAccepted(answerRecord.getAccepted());
            answer.setVoteCount(answerRecord.getVoteCount());
            answer.setFlagCount(answerRecord.getFlagCount());
            answer.setCreationTime(answerRecord.getCreationDate().toLocalDateTime());
            answer.setAuthor(answerRecord.getAuthorId());
            answer.setQuestion(answerRecord.getQuestionId());
            answer.setPhotos(photoRepository.findByAnswerId(answerRecord.getId()));
            answer.setComments(commentRepository.findByAnswerId(answerRecord.getId()));
        return answer;
    }
}
